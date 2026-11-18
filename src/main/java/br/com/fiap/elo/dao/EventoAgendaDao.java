package br.com.fiap.elo.dao;

import br.com.fiap.elo.model.EventoAgenda;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/** Acesso a dados da tabela TB_EVENTO_AGENDA (JDBC via JdbcTemplate). */
@Repository
public class EventoAgendaDao extends BaseDao<EventoAgenda> {

    public EventoAgendaDao(JdbcTemplate jdbc) {
        super(jdbc, "TB_EVENTO_AGENDA", "ID_EVENTO", EventoAgendaDao::mapear);
    }

    private static EventoAgenda mapear(ResultSet rs, int linha) throws SQLException {
        return new EventoAgenda(
                Conv.id(rs, "ID_EVENTO"),
                Conv.id(rs, "ID_NUCLEO"),
                Conv.id(rs, "ID_PESSOA_CUIDADA"),
                Conv.id(rs, "ID_PARTICIPANTE_RESP"),
                rs.getString("TITULO"),
                rs.getString("TIPO_EVENTO"),
                Conv.dataHora(rs, "DATA_EVENTO"),
                rs.getString("LOCAL_EVENTO"),
                rs.getString("STATUS_EVENTO"));
    }

    @Override
    public EventoAgenda inserir(EventoAgenda o) {
        Long id = proximoId("SEQ_EVENTO");
        jdbc.update("INSERT INTO TB_EVENTO_AGENDA (ID_EVENTO, ID_NUCLEO, ID_PESSOA_CUIDADA, ID_PARTICIPANTE_RESP, TITULO, TIPO_EVENTO, DATA_EVENTO, LOCAL_EVENTO, STATUS_EVENTO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                id, o.idNucleo(), o.idPessoaCuidada(), o.idParticipanteResp(), o.titulo(), o.tipoEvento(), Conv.dataHora(o.dataEvento()), o.localEvento(), o.statusEvento());
        return buscarPorId(id).orElseThrow();
    }

    @Override
    public boolean atualizar(Long id, EventoAgenda o) {
        return jdbc.update("UPDATE TB_EVENTO_AGENDA SET ID_NUCLEO = ?, ID_PESSOA_CUIDADA = ?, ID_PARTICIPANTE_RESP = ?, TITULO = ?, TIPO_EVENTO = ?, DATA_EVENTO = ?, LOCAL_EVENTO = ?, STATUS_EVENTO = ? WHERE ID_EVENTO = ?",
                o.idNucleo(), o.idPessoaCuidada(), o.idParticipanteResp(), o.titulo(), o.tipoEvento(), Conv.dataHora(o.dataEvento()), o.localEvento(), o.statusEvento(), id) > 0;
    }
}
