package br.com.fiap.elo.bo;

import br.com.fiap.elo.dao.BaseDao;
import br.com.fiap.elo.exception.RecursoNaoEncontradoException;

import java.util.List;

/** Fluxo comum de CRUD. Cada BO concreto aplica suas regras em {@link #preparar}. */
public abstract class BaseBo<T> {

    protected final BaseDao<T> dao;
    private final String nomeRecurso;

    protected BaseBo(BaseDao<T> dao, String nomeRecurso) {
        this.dao = dao;
        this.nomeRecurso = nomeRecurso;
    }

    /** Valida regras de negócio e devolve o objeto normalizado (defaults, maiúsculas, etc.). */
    protected T preparar(T objeto, boolean criacao) {
        return objeto;
    }

    public List<T> listar() {
        return dao.listar();
    }

    public List<T> listarPor(String coluna, Long valor) {
        return valor == null ? dao.listar() : dao.listarPorColuna(coluna, valor);
    }

    public T buscar(Long id) {
        return dao.buscarPorId(id).orElseThrow(() -> new RecursoNaoEncontradoException(nomeRecurso, id));
    }

    public T criar(T objeto) {
        return dao.inserir(preparar(objeto, true));
    }

    public T atualizar(Long id, T objeto) {
        buscar(id);
        dao.atualizar(id, preparar(objeto, false));
        return buscar(id);
    }

    public void excluir(Long id) {
        buscar(id);
        dao.excluir(id);
    }
}
