package br.com.fiap.elo.dao;

import br.com.fiap.elo.model.RedeApoio;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/** Acesso a dados da tabela TB_REDE_APOIO (JDBC via JdbcTemplate). */
@Repository
public class RedeApoioDao extends BaseDao<RedeApoio> {

    public RedeApoioDao(JdbcTemplate jdbc) {
        super(jdbc, "TB_REDE_APOIO", "ID_APOIO", RedeApoioDao::mapear);
    }

    private static RedeApoio mapear(ResultSet rs, int linha) throws SQLException {
        return new RedeApoio(
                Conv.id(rs, "ID_APOIO"),
                rs.getString("NOME_INSTITUICAO"),
                rs.getString("TIPO_INSTITUICAO"),
                rs.getString("CIDADE"),
                rs.getString("UF"),
                rs.getString("TELEFONE"),
                rs.getString("PUBLICO_ATENDIDO"),
                rs.getString("DESCRICAO"));
    }

    @Override
    public RedeApoio inserir(RedeApoio o) {
        Long id = proximoId("SEQ_APOIO");
        jdbc.update("INSERT INTO TB_REDE_APOIO (ID_APOIO, NOME_INSTITUICAO, TIPO_INSTITUICAO, CIDADE, UF, TELEFONE, PUBLICO_ATENDIDO, DESCRICAO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                id, o.nomeInstituicao(), o.tipoInstituicao(), o.cidade(), o.uf(), o.telefone(), o.publicoAtendido(), o.descricao());
        return buscarPorId(id).orElseThrow();
    }

    @Override
    public boolean atualizar(Long id, RedeApoio o) {
        return jdbc.update("UPDATE TB_REDE_APOIO SET NOME_INSTITUICAO = ?, TIPO_INSTITUICAO = ?, CIDADE = ?, UF = ?, TELEFONE = ?, PUBLICO_ATENDIDO = ?, DESCRICAO = ? WHERE ID_APOIO = ?",
                o.nomeInstituicao(), o.tipoInstituicao(), o.cidade(), o.uf(), o.telefone(), o.publicoAtendido(), o.descricao(), id) > 0;
    }

    public List<RedeApoio> listarPorCidade(String cidade) {
        return jdbc.query("SELECT * FROM TB_REDE_APOIO WHERE UPPER(CIDADE) = UPPER(?) ORDER BY NOME_INSTITUICAO",
                mapper(), cidade);
    }
}
