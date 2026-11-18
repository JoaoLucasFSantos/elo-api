package br.com.fiap.elo.dao;

import br.com.fiap.elo.model.EncaminhamentoApoio;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/** Acesso a dados da tabela TB_ENCAMINHAMENTO_APOIO (JDBC via JdbcTemplate). */
@Repository
public class EncaminhamentoApoioDao extends BaseDao<EncaminhamentoApoio> {

    public EncaminhamentoApoioDao(JdbcTemplate jdbc) {
        super(jdbc, "TB_ENCAMINHAMENTO_APOIO", "ID_ENCAMINHAMENTO", EncaminhamentoApoioDao::mapear);
    }

    private static EncaminhamentoApoio mapear(ResultSet rs, int linha) throws SQLException {
        return new EncaminhamentoApoio(
                Conv.id(rs, "ID_ENCAMINHAMENTO"),
                Conv.id(rs, "ID_NUCLEO"),
                Conv.id(rs, "ID_APOIO"),
                Conv.data(rs, "DT_ENCAMINHAMENTO"),
                rs.getString("STATUS"),
                rs.getString("OBSERVACAO"));
    }

    @Override
    public EncaminhamentoApoio inserir(EncaminhamentoApoio o) {
        Long id = proximoId("SEQ_ENCAMINHAMENTO");
        jdbc.update("INSERT INTO TB_ENCAMINHAMENTO_APOIO (ID_ENCAMINHAMENTO, ID_NUCLEO, ID_APOIO, DT_ENCAMINHAMENTO, STATUS, OBSERVACAO) VALUES (?, ?, ?, ?, ?, ?)",
                id, o.idNucleo(), o.idApoio(), Conv.data(o.dataEncaminhamento()), o.status(), o.observacao());
        return buscarPorId(id).orElseThrow();
    }

    @Override
    public boolean atualizar(Long id, EncaminhamentoApoio o) {
        return jdbc.update("UPDATE TB_ENCAMINHAMENTO_APOIO SET ID_NUCLEO = ?, ID_APOIO = ?, DT_ENCAMINHAMENTO = ?, STATUS = ?, OBSERVACAO = ? WHERE ID_ENCAMINHAMENTO = ?",
                o.idNucleo(), o.idApoio(), Conv.data(o.dataEncaminhamento()), o.status(), o.observacao(), id) > 0;
    }
}
