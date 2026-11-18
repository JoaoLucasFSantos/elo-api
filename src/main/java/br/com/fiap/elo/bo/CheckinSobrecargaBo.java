package br.com.fiap.elo.bo;

import br.com.fiap.elo.dao.CheckinSobrecargaDao;
import br.com.fiap.elo.dao.ParticipanteNucleoDao;
import br.com.fiap.elo.model.CheckinSobrecarga;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CheckinSobrecargaBo extends BaseBo<CheckinSobrecarga> {

    private final ParticipanteNucleoDao participanteDao;

    public CheckinSobrecargaBo(CheckinSobrecargaDao dao, ParticipanteNucleoDao participanteDao) {
        super(dao, "Check-in");
        this.participanteDao = participanteDao;
    }

    @Override
    protected CheckinSobrecarga preparar(CheckinSobrecarga c, boolean criacao) {
        Regras.existe(participanteDao.buscarPorId(c.idParticipante()),
                "O participante " + c.idParticipante() + " não existe.");
        LocalDate data = c.dataCheckin() != null ? c.dataCheckin() : LocalDate.now();
        Regras.exigir(!data.isAfter(LocalDate.now()), "A data do check-in não pode estar no futuro.");
        return new CheckinSobrecarga(null, c.idParticipante(), data, c.nivelSobrecarga(),
                c.horasCuidadoSemana(), c.precisouAjuda(), c.comentario());
    }
}
