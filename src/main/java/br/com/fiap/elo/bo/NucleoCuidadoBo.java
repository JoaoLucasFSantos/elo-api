package br.com.fiap.elo.bo;

import br.com.fiap.elo.dao.NucleoCuidadoDao;
import br.com.fiap.elo.model.NucleoCuidado;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class NucleoCuidadoBo extends BaseBo<NucleoCuidado> {

    public NucleoCuidadoBo(NucleoCuidadoDao dao) {
        super(dao, "Núcleo");
    }

    @Override
    protected NucleoCuidado preparar(NucleoCuidado n, boolean criacao) {
        LocalDate criado = n.dataCriacao() != null ? n.dataCriacao() : LocalDate.now();
        return new NucleoCuidado(null, n.nome().trim(), n.descricao(), n.cidade().trim(),
                n.uf().toUpperCase(), criado);
    }
}
