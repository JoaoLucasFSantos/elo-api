package br.com.fiap.elo.bo;

import br.com.fiap.elo.dao.EventoAgendaDao;
import br.com.fiap.elo.dao.ParticipanteNucleoDao;
import br.com.fiap.elo.dao.PessoaCuidadaDao;
import br.com.fiap.elo.model.EventoAgenda;
import br.com.fiap.elo.model.ParticipanteNucleo;
import br.com.fiap.elo.model.PessoaCuidada;
import org.springframework.stereotype.Service;

@Service
public class EventoAgendaBo extends BaseBo<EventoAgenda> {

    private final PessoaCuidadaDao pessoaDao;
    private final ParticipanteNucleoDao participanteDao;

    public EventoAgendaBo(EventoAgendaDao dao, PessoaCuidadaDao pessoaDao, ParticipanteNucleoDao participanteDao) {
        super(dao, "Evento");
        this.pessoaDao = pessoaDao;
        this.participanteDao = participanteDao;
    }

    @Override
    protected EventoAgenda preparar(EventoAgenda e, boolean criacao) {
        PessoaCuidada pessoa = Regras.existe(pessoaDao.buscarPorId(e.idPessoaCuidada()),
                "A pessoa cuidada " + e.idPessoaCuidada() + " não existe.");
        Regras.exigir(pessoa.idNucleo().equals(e.idNucleo()), "A pessoa cuidada pertence a outro núcleo.");
        if (e.idParticipanteResp() != null) {
            ParticipanteNucleo resp = Regras.existe(participanteDao.buscarPorId(e.idParticipanteResp()),
                    "O participante responsável " + e.idParticipanteResp() + " não existe.");
            Regras.exigir(resp.idNucleo().equals(e.idNucleo()), "O responsável pertence a outro núcleo.");
            Regras.exigir(Boolean.TRUE.equals(resp.ativo()), "O responsável está inativo.");
        }
        String status = e.statusEvento() == null ? "AGENDADO" : e.statusEvento().toUpperCase();
        return new EventoAgenda(null, e.idNucleo(), e.idPessoaCuidada(), e.idParticipanteResp(), e.titulo().trim(),
                e.tipoEvento().toUpperCase(), e.dataEvento(), e.localEvento(), status);
    }
}
