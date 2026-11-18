package br.com.fiap.elo.bo;

import br.com.fiap.elo.dao.EncaminhamentoApoioDao;
import br.com.fiap.elo.dao.NucleoCuidadoDao;
import br.com.fiap.elo.dao.RedeApoioDao;
import br.com.fiap.elo.model.EncaminhamentoApoio;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EncaminhamentoApoioBo extends BaseBo<EncaminhamentoApoio> {

    private final NucleoCuidadoDao nucleoDao;
    private final RedeApoioDao apoioDao;

    public EncaminhamentoApoioBo(EncaminhamentoApoioDao dao, NucleoCuidadoDao nucleoDao, RedeApoioDao apoioDao) {
        super(dao, "Encaminhamento");
        this.nucleoDao = nucleoDao;
        this.apoioDao = apoioDao;
    }

    @Override
    protected EncaminhamentoApoio preparar(EncaminhamentoApoio e, boolean criacao) {
        Regras.existe(nucleoDao.buscarPorId(e.idNucleo()), "O núcleo " + e.idNucleo() + " não existe.");
        Regras.existe(apoioDao.buscarPorId(e.idApoio()), "A instituição " + e.idApoio() + " não existe.");
        LocalDate data = e.dataEncaminhamento() != null ? e.dataEncaminhamento() : LocalDate.now();
        String status = e.status() == null ? "SUGERIDO" : e.status().toUpperCase();
        return new EncaminhamentoApoio(null, e.idNucleo(), e.idApoio(), data, status, e.observacao());
    }
}
