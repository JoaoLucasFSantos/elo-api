package br.com.fiap.elo.dao;

import br.com.fiap.elo.model.NucleoCuidado;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/** Acesso a dados da tabela TB_NUCLEO_CUIDADO (JDBC via JdbcTemplate). */
@Repository
public class NucleoCuidadoDao extends BaseDao<NucleoCuidado> {

    public NucleoCuidadoDao(JdbcTemplate jdbc) {
        super(jdbc, "TB_NUCLEO_CUIDADO", "ID_NUCLEO", NucleoCuidadoDao::mapear);
    }

    private static NucleoCuidado mapear(ResultSet rs, int linha) throws SQLException {
        return new NucleoCuidado(
                Conv.id(rs, "ID_NUCLEO"),
                rs.getString("NOME_NUCLEO"),
                rs.getString("DESCRICAO"),
                rs.getString("CIDADE"),
                rs.getString("UF"),
                Conv.data(rs, "DT_CRIACAO"));
    }

    @Override
    public NucleoCuidado inserir(NucleoCuidado o) {
        Long id = proximoId("SEQ_NUCLEO");
        jdbc.update("INSERT INTO TB_NUCLEO_CUIDADO (ID_NUCLEO, NOME_NUCLEO, DESCRICAO, CIDADE, UF, DT_CRIACAO) VALUES (?, ?, ?, ?, ?, ?)",
                id, o.nome(), o.descricao(), o.cidade(), o.uf(), Conv.data(o.dataCriacao()));
        return buscarPorId(id).orElseThrow();
    }

    @Override
    public boolean atualizar(Long id, NucleoCuidado o) {
        return jdbc.update("UPDATE TB_NUCLEO_CUIDADO SET NOME_NUCLEO = ?, DESCRICAO = ?, CIDADE = ?, UF = ? WHERE ID_NUCLEO = ?",
                o.nome(), o.descricao(), o.cidade(), o.uf(), id) > 0;
    }
}
