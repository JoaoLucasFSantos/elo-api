package br.com.fiap.elo.dao;

import br.com.fiap.elo.model.TarefaCuidado;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/** Acesso a dados da tabela TB_TAREFA_CUIDADO (JDBC via JdbcTemplate). */
@Repository
public class TarefaCuidadoDao extends BaseDao<TarefaCuidado> {

    public TarefaCuidadoDao(JdbcTemplate jdbc) {
        super(jdbc, "TB_TAREFA_CUIDADO", "ID_TAREFA", TarefaCuidadoDao::mapear);
    }

    private static TarefaCuidado mapear(ResultSet rs, int linha) throws SQLException {
        return new TarefaCuidado(
                Conv.id(rs, "ID_TAREFA"),
                Conv.id(rs, "ID_NUCLEO"),
                Conv.id(rs, "ID_PESSOA_CUIDADA"),
                rs.getString("TITULO"),
                rs.getString("CATEGORIA"),
                rs.getString("FREQUENCIA"),
                Conv.inteiro(rs, "TEMPO_ESTIMADO_MIN"),
                rs.getString("PRIORIDADE"));
    }

    @Override
    public TarefaCuidado inserir(TarefaCuidado o) {
        Long id = proximoId("SEQ_TAREFA");
        jdbc.update("INSERT INTO TB_TAREFA_CUIDADO (ID_TAREFA, ID_NUCLEO, ID_PESSOA_CUIDADA, TITULO, CATEGORIA, FREQUENCIA, TEMPO_ESTIMADO_MIN, PRIORIDADE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                id, o.idNucleo(), o.idPessoaCuidada(), o.titulo(), o.categoria(), o.frequencia(), o.tempoEstimadoMin(), o.prioridade());
        return buscarPorId(id).orElseThrow();
    }

    @Override
    public boolean atualizar(Long id, TarefaCuidado o) {
        return jdbc.update("UPDATE TB_TAREFA_CUIDADO SET ID_NUCLEO = ?, ID_PESSOA_CUIDADA = ?, TITULO = ?, CATEGORIA = ?, FREQUENCIA = ?, TEMPO_ESTIMADO_MIN = ?, PRIORIDADE = ? WHERE ID_TAREFA = ?",
                o.idNucleo(), o.idPessoaCuidada(), o.titulo(), o.categoria(), o.frequencia(), o.tempoEstimadoMin(), o.prioridade(), id) > 0;
    }
}
