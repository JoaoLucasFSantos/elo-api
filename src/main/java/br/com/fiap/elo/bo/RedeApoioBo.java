package br.com.fiap.elo.bo;

import br.com.fiap.elo.dao.NucleoCuidadoDao;
import br.com.fiap.elo.dao.RedeApoioDao;
import br.com.fiap.elo.exception.RecursoNaoEncontradoException;
import br.com.fiap.elo.model.NucleoCuidado;
import br.com.fiap.elo.model.RedeApoio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedeApoioBo extends BaseBo<RedeApoio> {

    private final RedeApoioDao apoioDao;
    private final NucleoCuidadoDao nucleoDao;

    public RedeApoioBo(RedeApoioDao dao, NucleoCuidadoDao nucleoDao) {
        super(dao, "Instituição de apoio");
        this.apoioDao = dao;
        this.nucleoDao = nucleoDao;
    }

    @Override
    protected RedeApoio preparar(RedeApoio r, boolean criacao) {
        return new RedeApoio(null, r.nomeInstituicao().trim(), r.tipoInstituicao().trim().toUpperCase(),
                r.cidade().trim(), r.uf().toUpperCase(), r.telefone(), r.publicoAtendido(), r.descricao());
    }

    /** Sugere instituições de apoio da mesma cidade do núcleo. */
    public List<RedeApoio> sugerirParaNucleo(Long idNucleo) {
        NucleoCuidado nucleo = nucleoDao.buscarPorId(idNucleo)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Núcleo", idNucleo));
        return apoioDao.listarPorCidade(nucleo.cidade());
    }
}
