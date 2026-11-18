package br.com.fiap.elo.bo;

import br.com.fiap.elo.dao.AtribuicaoTarefaDao;
import br.com.fiap.elo.dao.CheckinSobrecargaDao;
import br.com.fiap.elo.dao.NucleoCuidadoDao;
import br.com.fiap.elo.dao.ParticipanteNucleoDao;
import br.com.fiap.elo.dao.TarefaCuidadoDao;
import br.com.fiap.elo.dao.UsuarioDao;
import br.com.fiap.elo.dto.IndicadorParticipante;
import br.com.fiap.elo.dto.IndicadoresNucleo;
import br.com.fiap.elo.dto.SugestaoRedistribuicao;
import br.com.fiap.elo.exception.RecursoNaoEncontradoException;
import br.com.fiap.elo.model.AtribuicaoTarefa;
import br.com.fiap.elo.model.CheckinSobrecarga;
import br.com.fiap.elo.model.NucleoCuidado;
import br.com.fiap.elo.model.ParticipanteNucleo;
import br.com.fiap.elo.model.TarefaCuidado;
import br.com.fiap.elo.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Indicadores de impacto e sugestões de redistribuição de tarefas de um núcleo.
 * A carga usada é a registrada em CARGA_ATUAL_HORAS; a redistribuição transfere horas semanais entre participantes.
 */
@Service
public class IndicadorBo {

    private final NucleoCuidadoDao nucleoDao;
    private final ParticipanteNucleoDao participanteDao;
    private final UsuarioDao usuarioDao;
    private final CheckinSobrecargaDao checkinDao;
    private final AtribuicaoTarefaDao atribuicaoDao;
    private final TarefaCuidadoDao tarefaDao;

    public IndicadorBo(NucleoCuidadoDao nucleoDao, ParticipanteNucleoDao participanteDao, UsuarioDao usuarioDao,
                       CheckinSobrecargaDao checkinDao, AtribuicaoTarefaDao atribuicaoDao,
                       TarefaCuidadoDao tarefaDao) {
        this.nucleoDao = nucleoDao;
        this.participanteDao = participanteDao;
        this.usuarioDao = usuarioDao;
        this.checkinDao = checkinDao;
        this.atribuicaoDao = atribuicaoDao;
        this.tarefaDao = tarefaDao;
    }

    /** Converte a frequência da tarefa em horas por semana (diária = 7x, semanal = 1x, mensal = 1/4). */
    public static double horasSemanais(TarefaCuidado t) {
        double horas = t.tempoEstimadoMin() / 60.0;
        return switch (t.frequencia()) {
            case "DIARIA" -> horas * 7;
            case "MENSAL" -> horas / 4.0;
            default -> horas;
        };
    }

    public IndicadoresNucleo indicadores(Long idNucleo) {
        NucleoCuidado nucleo = exigirNucleo(idNucleo);
        List<ParticipanteNucleo> participantes = participanteDao.listarPorColuna("ID_NUCLEO", idNucleo);

        double total = 0;
        double maior = 0;
        for (ParticipanteNucleo p : participantes) {
            total += p.cargaAtualHoras();
            maior = Math.max(maior, p.cargaAtualHoras());
        }
        double concentracao = total > 0 ? arredondar(maior / total * 100) : 0.0;

        Map<String, Integer> porStatus = new LinkedHashMap<>();
        porStatus.put("PENDENTE", 0);
        porStatus.put("EM_ANDAMENTO", 0);
        porStatus.put("CONCLUIDA", 0);
        for (AtribuicaoTarefa a : atribuicaoDao.listarPorColuna("ID_NUCLEO", idNucleo)) {
            porStatus.merge(a.status(), 1, Integer::sum);
        }

        List<IndicadorParticipante> lista = participantes.stream().map(this::indicador).toList();
        return new IndicadoresNucleo(nucleo.id(), nucleo.nome(), arredondar(total), concentracao, porStatus, lista);
    }

    private IndicadorParticipante indicador(ParticipanteNucleo p) {
        double disp = p.disponibilidadeHorasSemana();
        double carga = p.cargaAtualHoras();
        Double ocupacao = disp > 0 ? arredondar(carga / disp * 100) : null;
        Integer nivel = checkinDao.ultimoDoParticipante(p.id())
                .map(CheckinSobrecarga::nivelSobrecarga).orElse(null);

        boolean semCapacidade = disp == 0 && carga > 0;
        String situacao;
        if (semCapacidade || (ocupacao != null && ocupacao > 100) || (nivel != null && nivel >= 4)) {
            situacao = "SOBRECARREGADO";
        } else if ((ocupacao != null && ocupacao >= 80) || (nivel != null && nivel == 3)) {
            situacao = "ATENCAO";
        } else {
            situacao = "EQUILIBRADO";
        }
        return new IndicadorParticipante(p.id(), nomeDe(p), disp, carga, ocupacao, nivel, situacao);
    }

    /** Sugere mover tarefas em aberto de quem está acima da disponibilidade para quem tem folga. */
    public List<SugestaoRedistribuicao> sugerirRedistribuicao(Long idNucleo) {
        exigirNucleo(idNucleo);
        List<ParticipanteNucleo> ativos = participanteDao.listarPorColuna("ID_NUCLEO", idNucleo).stream()
                .filter(p -> Boolean.TRUE.equals(p.ativo())).toList();

        Map<Long, Double> carga = new HashMap<>();
        ativos.forEach(p -> carga.put(p.id(), p.cargaAtualHoras()));

        List<AtribuicaoTarefa> abertas = atribuicaoDao.listarPorColuna("ID_NUCLEO", idNucleo).stream()
                .filter(a -> !"CONCLUIDA".equals(a.status())).toList();

        List<ParticipanteNucleo> sobrecarregados = ativos.stream()
                .filter(p -> carga.get(p.id()) > p.disponibilidadeHorasSemana())
                .sorted(Comparator.comparingDouble(
                        (ParticipanteNucleo p) -> p.disponibilidadeHorasSemana() - p.cargaAtualHoras()))
                .toList();

        List<SugestaoRedistribuicao> sugestoes = new ArrayList<>();
        for (ParticipanteNucleo origem : sobrecarregados) {
            List<Item> itens = abertas.stream()
                    .filter(a -> a.idParticipante().equals(origem.id()))
                    .map(a -> {
                        TarefaCuidado t = tarefaDao.buscarPorId(a.idTarefa()).orElseThrow();
                        return new Item(a, t, horasSemanais(t));
                    })
                    .sorted(Comparator.comparingDouble(Item::horas).reversed())
                    .toList();

            for (Item item : itens) {
                if (carga.get(origem.id()) <= origem.disponibilidadeHorasSemana()) break;
                ParticipanteNucleo destino = ativos.stream()
                        .filter(p -> !p.id().equals(origem.id()))
                        .filter(p -> p.disponibilidadeHorasSemana() - carga.get(p.id()) >= item.horas())
                        .max(Comparator.comparingDouble(p -> p.disponibilidadeHorasSemana() - carga.get(p.id())))
                        .orElse(null);
                if (destino == null) continue;

                carga.merge(origem.id(), -item.horas(), Double::sum);
                carga.merge(destino.id(), item.horas(), Double::sum);
                String deNome = nomeDe(origem);
                String paraNome = nomeDe(destino);
                sugestoes.add(new SugestaoRedistribuicao(item.atribuicao().id(), item.tarefa().titulo(),
                        origem.id(), deNome, destino.id(), paraNome, arredondar(item.horas()),
                        "Reduz a carga de " + deNome + " usando a folga de " + paraNome + "."));
            }
        }
        return sugestoes;
    }

    private NucleoCuidado exigirNucleo(Long idNucleo) {
        return nucleoDao.buscarPorId(idNucleo)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Núcleo", idNucleo));
    }

    private String nomeDe(ParticipanteNucleo p) {
        return usuarioDao.buscarPorId(p.idUsuario()).map(Usuario::nome).orElse("Participante " + p.id());
    }

    private static double arredondar(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    private record Item(AtribuicaoTarefa atribuicao, TarefaCuidado tarefa, double horas) {}
}
