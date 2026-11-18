package br.com.fiap.elo.dao;

import br.com.fiap.elo.model.CheckinSobrecarga;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/** Acesso a dados da tabela TB_CHECKIN_SOBRECARGA (JDBC via JdbcTemplate). */
@Repository
public class CheckinSobrecargaDao extends BaseDao<CheckinSobrecarga> {

    public CheckinSobrecargaDao(JdbcTemplate jdbc) {
        super(jdbc, "TB_CHECKIN_SOBRECARGA", "ID_CHECKIN", CheckinSobrecargaDao::mapear);
    }

    private static CheckinSobrecarga mapear(ResultSet rs, int linha) throws SQLException {
        return new CheckinSobrecarga(
                Conv.id(rs, "ID_CHECKIN"),
                Conv.id(rs, "ID_PARTICIPANTE"),
                Conv.data(rs, "DATA_CHECKIN"),
                Conv.inteiro(rs, "NIVEL_SOBRECARGA"),
                Conv.num(rs, "HORAS_CUIDADO_SEMANA"),
                Conv.deSN(rs.getString("PRECISOU_AJUDA")),
                rs.getString("COMENTARIO"));
    }

    @Override
    public CheckinSobrecarga inserir(CheckinSobrecarga o) {
        Long id = proximoId("SEQ_CHECKIN");
        jdbc.update("INSERT INTO TB_CHECKIN_SOBRECARGA (ID_CHECKIN, ID_PARTICIPANTE, DATA_CHECKIN, NIVEL_SOBRECARGA, HORAS_CUIDADO_SEMANA, PRECISOU_AJUDA, COMENTARIO) VALUES (?, ?, ?, ?, ?, ?, ?)",
                id, o.idParticipante(), Conv.data(o.dataCheckin()), o.nivelSobrecarga(), o.horasCuidadoSemana(), Conv.paraSN(o.precisouAjuda(), false), o.comentario());
        return buscarPorId(id).orElseThrow();
    }

    @Override
    public boolean atualizar(Long id, CheckinSobrecarga o) {
        return jdbc.update("UPDATE TB_CHECKIN_SOBRECARGA SET ID_PARTICIPANTE = ?, DATA_CHECKIN = ?, NIVEL_SOBRECARGA = ?, HORAS_CUIDADO_SEMANA = ?, PRECISOU_AJUDA = ?, COMENTARIO = ? WHERE ID_CHECKIN = ?",
                o.idParticipante(), Conv.data(o.dataCheckin()), o.nivelSobrecarga(), o.horasCuidadoSemana(), Conv.paraSN(o.precisouAjuda(), false), o.comentario(), id) > 0;
    }

    public Optional<CheckinSobrecarga> ultimoDoParticipante(Long idParticipante) {
        return jdbc.query("SELECT * FROM TB_CHECKIN_SOBRECARGA WHERE ID_PARTICIPANTE = ? "
                + "ORDER BY DATA_CHECKIN DESC, ID_CHECKIN DESC FETCH FIRST 1 ROWS ONLY",
                mapper(), idParticipante).stream().findFirst();
    }
}
