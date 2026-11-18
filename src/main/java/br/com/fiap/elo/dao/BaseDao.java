package br.com.fiap.elo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

/** Operações comuns de acesso a dados. Cada DAO concreto define INSERT e UPDATE da sua tabela. */
public abstract class BaseDao<T> {

    protected final JdbcTemplate jdbc;
    private final String tabela;
    private final String colunaId;
    private final RowMapper<T> mapper;

    protected BaseDao(JdbcTemplate jdbc, String tabela, String colunaId, RowMapper<T> mapper) {
        this.jdbc = jdbc;
        this.tabela = tabela;
        this.colunaId = colunaId;
        this.mapper = mapper;
    }

    protected RowMapper<T> mapper() { return mapper; }

    public List<T> listar() {
        return jdbc.query("SELECT * FROM " + tabela + " ORDER BY " + colunaId, mapper);
    }

    /** O nome da coluna vem sempre do código (nunca do usuário), por isso a concatenação é segura. */
    public List<T> listarPorColuna(String coluna, Object valor) {
        return jdbc.query("SELECT * FROM " + tabela + " WHERE " + coluna + " = ? ORDER BY " + colunaId, mapper, valor);
    }

    public Optional<T> buscarPorId(Long id) {
        return jdbc.query("SELECT * FROM " + tabela + " WHERE " + colunaId + " = ?", mapper, id)
                .stream().findFirst();
    }

    public boolean excluir(Long id) {
        return jdbc.update("DELETE FROM " + tabela + " WHERE " + colunaId + " = ?", id) > 0;
    }

    protected Long proximoId(String sequencia) {
        return jdbc.queryForObject("SELECT " + sequencia + ".NEXTVAL FROM DUAL", Long.class);
    }

    public abstract T inserir(T objeto);

    public abstract boolean atualizar(Long id, T objeto);
}
