package br.com.fiap.elo.dao;

import br.com.fiap.elo.model.PessoaCuidada;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/** Acesso a dados da tabela TB_PESSOA_CUIDADA (JDBC via JdbcTemplate). */
@Repository
public class PessoaCuidadaDao extends BaseDao<PessoaCuidada> {

    public PessoaCuidadaDao(JdbcTemplate jdbc) {
        super(jdbc, "TB_PESSOA_CUIDADA", "ID_PESSOA_CUIDADA", PessoaCuidadaDao::mapear);
    }

    private static PessoaCuidada mapear(ResultSet rs, int linha) throws SQLException {
        return new PessoaCuidada(
                Conv.id(rs, "ID_PESSOA_CUIDADA"),
                Conv.id(rs, "ID_NUCLEO"),
                rs.getString("NOME_COMPLETO"),
                Conv.data(rs, "DATA_NASCIMENTO"),
                rs.getString("GRAU_DEPENDENCIA"),
                rs.getString("OBSERVACOES"),
                rs.getString("CONTATO_EMERGENCIA"));
    }

    @Override
    public PessoaCuidada inserir(PessoaCuidada o) {
        Long id = proximoId("SEQ_PESSOA");
        jdbc.update("INSERT INTO TB_PESSOA_CUIDADA (ID_PESSOA_CUIDADA, ID_NUCLEO, NOME_COMPLETO, DATA_NASCIMENTO, GRAU_DEPENDENCIA, OBSERVACOES, CONTATO_EMERGENCIA) VALUES (?, ?, ?, ?, ?, ?, ?)",
                id, o.idNucleo(), o.nome(), Conv.data(o.dataNascimento()), o.grauDependencia(), o.observacoes(), o.contatoEmergencia());
        return buscarPorId(id).orElseThrow();
    }

    @Override
    public boolean atualizar(Long id, PessoaCuidada o) {
        return jdbc.update("UPDATE TB_PESSOA_CUIDADA SET ID_NUCLEO = ?, NOME_COMPLETO = ?, DATA_NASCIMENTO = ?, GRAU_DEPENDENCIA = ?, OBSERVACOES = ?, CONTATO_EMERGENCIA = ? WHERE ID_PESSOA_CUIDADA = ?",
                o.idNucleo(), o.nome(), Conv.data(o.dataNascimento()), o.grauDependencia(), o.observacoes(), o.contatoEmergencia(), id) > 0;
    }
}
