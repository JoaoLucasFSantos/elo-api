package br.com.fiap.elo.bo;

import br.com.fiap.elo.dao.AtribuicaoTarefaDao;
import br.com.fiap.elo.dao.ParticipanteNucleoDao;
import br.com.fiap.elo.dao.TarefaCuidadoDao;
import br.com.fiap.elo.model.AtribuicaoTarefa;
import br.com.fiap.elo.model.ParticipanteNucleo;
import br.com.fiap.elo.model.TarefaCuidado;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class AtribuicaoTarefaBo extends BaseBo<AtribuicaoTarefa> {

    private final AtribuicaoTarefaDao atribuicaoDao;
    private final TarefaCuidadoDao tarefaDao;
    private final ParticipanteNucleoDao participanteDao;

    public AtribuicaoTarefaBo(AtribuicaoTarefaDao dao, TarefaCuidadoDao tarefaDao,
                              ParticipanteNucleoDao participanteDao) {
        super(dao, "Atribuição");
        this.atribuicaoDao = dao;
        this.tarefaDao = tarefaDao;
        this.participanteDao = participanteDao;
    }

    @Override
    protected AtribuicaoTarefa preparar(AtribuicaoTarefa a, boolean criacao) {
        TarefaCuidado tarefa = Regras.existe(tarefaDao.buscarPorId(a.idTarefa()),
                "A tarefa " + a.idTarefa() + " não existe.");
        Regras.exigir(tarefa.idNucleo().equals(a.idNucleo()), "A tarefa pertence a outro núcleo.");
        ParticipanteNucleo p = Regras.existe(participanteDao.buscarPorId(a.idParticipante()),
                "O participante " + a.idParticipante() + " não existe.");
        Regras.exigir(p.idNucleo().equals(a.idNucleo()), "O participante pertence a outro núcleo.");
        Regras.exigir(Boolean.TRUE.equals(p.ativo()), "O participante está inativo.");

        String status = a.status() == null ? "PENDENTE" : a.status().toUpperCase();
        LocalDate conclusao = a.dataConclusao();
        if ("CONCLUIDA".equals(status)) {
            if (conclusao == null) conclusao = LocalDate.now();
        } else {
            Regras.exigir(conclusao == null,
                    "A data de conclusão só pode ser informada quando o status é CONCLUIDA.");
        }
        Regras.exigir(conclusao == null || !conclusao.isBefore(a.dataPrevista().minusDays(1)),
                "A data de conclusão não pode ser anterior a um dia antes da data prevista.");
        return new AtribuicaoTarefa(null, a.idNucleo(), a.idTarefa(), a.idParticipante(), a.dataPrevista(),
                status, conclusao, a.observacao());
    }

    /**
     * Move uma tarefa em aberto para outro participante do mesmo núcleo e transfere as horas semanais
     * da tarefa entre as cargas dos dois participantes. Tudo em uma única transação.
     */
    @Transactional
    public AtribuicaoTarefa reatribuir(Long idAtribuicao, Long idDestino) {
        AtribuicaoTarefa a = buscar(idAtribuicao);
        Regras.exigir(!"CONCLUIDA".equals(a.status()), "Tarefas concluídas não podem ser reatribuídas.");
        Regras.exigir(!a.idParticipante().equals(idDestino), "A tarefa já está atribuída a este participante.");
        ParticipanteNucleo destino = Regras.existe(participanteDao.buscarPorId(idDestino),
                "O participante de destino " + idDestino + " não existe.");
        Regras.exigir(destino.idNucleo().equals(a.idNucleo()), "O participante de destino pertence a outro núcleo.");
        Regras.exigir(Boolean.TRUE.equals(destino.ativo()), "O participante de destino está inativo.");
        ParticipanteNucleo origem = participanteDao.buscarPorId(a.idParticipante()).orElseThrow();
        TarefaCuidado tarefa = tarefaDao.buscarPorId(a.idTarefa()).orElseThrow();

        double horas = IndicadorBo.horasSemanais(tarefa);
        atribuicaoDao.atualizarParticipante(idAtribuicao, idDestino);
        participanteDao.atualizarCarga(origem.id(), arredondar(Math.max(0, origem.cargaAtualHoras() - horas)));
        participanteDao.atualizarCarga(destino.id(), arredondar(destino.cargaAtualHoras() + horas));
        return buscar(idAtribuicao);
    }

    private static double arredondar(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}
