package br.com.fiap.elo.dao;

import br.com.fiap.elo.model.ParticipanteNucleo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/** Acesso a dados da tabela TB_PARTICIPANTE_NUCLEO (JDBC via JdbcTemplate). */
@Repository
public class ParticipanteNucleoDao extends BaseDao<ParticipanteNucleo> {

    public ParticipanteNucleoDao(JdbcTemplate jdbc) {
        super(jdbc, "TB_PARTICIPANTE_NUCLEO", "ID_PARTICIPANTE", ParticipanteNucleoDao::mapear);
    }

    private static ParticipanteNucleo mapear(ResultSet rs, int linha) throws SQLException {
        return new ParticipanteNucleo(
                Conv.id(rs, "ID_PARTICIPANTE"),
                Conv.id(rs, "ID_NUCLEO"),
                Conv.id(rs, "ID_USUARIO"),
                rs.getString("PAPEL"),
                Conv.num(rs, "DISPONIBILIDADE_HORAS_SEMANA"),
                Conv.num(rs, "CARGA_ATUAL_HORAS"),
                Conv.deSN(rs.getString("ATIVO")));
    }

    @Override
    public ParticipanteNucleo inserir(ParticipanteNucleo o) {
        Long id = proximoId("SEQ_PARTICIPANTE");
        jdbc.update("INSERT INTO TB_PARTICIPANTE_NUCLEO (ID_PARTICIPANTE, ID_NUCLEO, ID_USUARIO, PAPEL, DISPONIBILIDADE_HORAS_SEMANA, CARGA_ATUAL_HORAS, ATIVO) VALUES (?, ?, ?, ?, ?, ?, ?)",
                id, o.idNucleo(), o.idUsuario(), o.papel(), o.disponibilidadeHorasSemana(), o.cargaAtualHoras(), Conv.paraSN(o.ativo(), true));
        return buscarPorId(id).orElseThrow();
    }

    @Override
    public boolean atualizar(Long id, ParticipanteNucleo o) {
        return jdbc.update("UPDATE TB_PARTICIPANTE_NUCLEO SET ID_NUCLEO = ?, ID_USUARIO = ?, PAPEL = ?, DISPONIBILIDADE_HORAS_SEMANA = ?, CARGA_ATUAL_HORAS = ?, ATIVO = ? WHERE ID_PARTICIPANTE = ?",
                o.idNucleo(), o.idUsuario(), o.papel(), o.disponibilidadeHorasSemana(), o.cargaAtualHoras(), Conv.paraSN(o.ativo(), true), id) > 0;
    }

    public void atualizarCarga(Long idParticipante, double carga) {
        jdbc.update("UPDATE TB_PARTICIPANTE_NUCLEO SET CARGA_ATUAL_HORAS = ? WHERE ID_PARTICIPANTE = ?",
                carga, idParticipante);
    }
}
