package br.com.fiap.elo.bo;

import br.com.fiap.elo.dao.NucleoCuidadoDao;
import br.com.fiap.elo.dao.PessoaCuidadaDao;
import br.com.fiap.elo.model.PessoaCuidada;
import org.springframework.stereotype.Service;

@Service
public class PessoaCuidadaBo extends BaseBo<PessoaCuidada> {

    private final NucleoCuidadoDao nucleoDao;

    public PessoaCuidadaBo(PessoaCuidadaDao dao, NucleoCuidadoDao nucleoDao) {
        super(dao, "Pessoa cuidada");
        this.nucleoDao = nucleoDao;
    }

    @Override
    protected PessoaCuidada preparar(PessoaCuidada p, boolean criacao) {
        Regras.existe(nucleoDao.buscarPorId(p.idNucleo()), "O núcleo " + p.idNucleo() + " não existe.");
        return new PessoaCuidada(null, p.idNucleo(), p.nome().trim(), p.dataNascimento(),
                p.grauDependencia().toUpperCase(), p.observacoes(), p.contatoEmergencia());
    }
}
