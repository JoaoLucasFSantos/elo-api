package br.com.fiap.elo.dao;

import br.com.fiap.elo.model.AtribuicaoTarefa;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/** Acesso a dados da tabela TB_ATRIBUICAO_TAREFA (JDBC via JdbcTemplate). */
@Repository
public class AtribuicaoTarefaDao extends BaseDao<AtribuicaoTarefa> {

    public AtribuicaoTarefaDao(JdbcTemplate jdbc) {
        super(jdbc, "TB_ATRIBUICAO_TAREFA", "ID_ATRIBUICAO", AtribuicaoTarefaDao::mapear);
    }

    private static AtribuicaoTarefa mapear(ResultSet rs, int linha) throws SQLException {
        return new AtribuicaoTarefa(
                Conv.id(rs, "ID_ATRIBUICAO"),
                Conv.id(rs, "ID_NUCLEO"),
                Conv.id(rs, "ID_TAREFA"),
                Conv.id(rs, "ID_PARTICIPANTE"),
                Conv.data(rs, "DATA_PREVISTA"),
                rs.getString("STATUS"),
                Conv.data(rs, "DATA_CONCLUSAO"),
                rs.getString("OBSERVACAO"));
    }

    @Override
    public AtribuicaoTarefa inserir(AtribuicaoTarefa o) {
        Long id = proximoId("SEQ_ATRIBUICAO");
        jdbc.update("INSERT INTO TB_ATRIBUICAO_TAREFA (ID_ATRIBUICAO, ID_NUCLEO, ID_TAREFA, ID_PARTICIPANTE, DATA_PREVISTA, STATUS, DATA_CONCLUSAO, OBSERVACAO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                id, o.idNucleo(), o.idTarefa(), o.idParticipante(), Conv.data(o.dataPrevista()), o.status(), Conv.data(o.dataConclusao()), o.observacao());
        return buscarPorId(id).orElseThrow();
    }

    @Override
    public boolean atualizar(Long id, AtribuicaoTarefa o) {
        return jdbc.update("UPDATE TB_ATRIBUICAO_TAREFA SET ID_NUCLEO = ?, ID_TAREFA = ?, ID_PARTICIPANTE = ?, DATA_PREVISTA = ?, STATUS = ?, DATA_CONCLUSAO = ?, OBSERVACAO = ? WHERE ID_ATRIBUICAO = ?",
                o.idNucleo(), o.idTarefa(), o.idParticipante(), Conv.data(o.dataPrevista()), o.status(), Conv.data(o.dataConclusao()), o.observacao(), id) > 0;
    }

    public void atualizarParticipante(Long idAtribuicao, Long idParticipante) {
        jdbc.update("UPDATE TB_ATRIBUICAO_TAREFA SET ID_PARTICIPANTE = ? WHERE ID_ATRIBUICAO = ?",
                idParticipante, idAtribuicao);
    }
}
