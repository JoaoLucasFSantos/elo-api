package br.com.fiap.elo.bo;

import br.com.fiap.elo.dao.PessoaCuidadaDao;
import br.com.fiap.elo.dao.TarefaCuidadoDao;
import br.com.fiap.elo.model.PessoaCuidada;
import br.com.fiap.elo.model.TarefaCuidado;
import org.springframework.stereotype.Service;

@Service
public class TarefaCuidadoBo extends BaseBo<TarefaCuidado> {

    private final PessoaCuidadaDao pessoaDao;

    public TarefaCuidadoBo(TarefaCuidadoDao dao, PessoaCuidadaDao pessoaDao) {
        super(dao, "Tarefa");
        this.pessoaDao = pessoaDao;
    }

    @Override
    protected TarefaCuidado preparar(TarefaCuidado t, boolean criacao) {
        PessoaCuidada pessoa = Regras.existe(pessoaDao.buscarPorId(t.idPessoaCuidada()),
                "A pessoa cuidada " + t.idPessoaCuidada() + " não existe.");
        Regras.exigir(pessoa.idNucleo().equals(t.idNucleo()),
                "A pessoa cuidada informada pertence a outro núcleo.");
        return new TarefaCuidado(null, t.idNucleo(), t.idPessoaCuidada(), t.titulo().trim(),
                t.categoria().trim().toUpperCase(), t.frequencia().toUpperCase(), t.tempoEstimadoMin(),
                t.prioridade().toUpperCase());
    }
}
