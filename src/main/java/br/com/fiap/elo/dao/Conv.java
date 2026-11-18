package br.com.fiap.elo.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/** Conversões entre tipos do Oracle (DATE, CHAR(1) 'S'/'N', NUMBER) e tipos Java. */
public final class Conv {
    private Conv() {}

    public static Date data(LocalDate d) { return d == null ? null : Date.valueOf(d); }

    public static Timestamp dataHora(LocalDateTime d) { return d == null ? null : Timestamp.valueOf(d); }

    public static LocalDate data(ResultSet rs, String col) throws SQLException {
        Date d = rs.getDate(col);
        return d == null ? null : d.toLocalDate();
    }

    public static LocalDateTime dataHora(ResultSet rs, String col) throws SQLException {
        Timestamp t = rs.getTimestamp(col);
        return t == null ? null : t.toLocalDateTime();
    }

    public static String paraSN(Boolean valor, boolean padrao) { return (valor == null ? padrao : valor) ? "S" : "N"; }

    public static Boolean deSN(String valor) { return "S".equalsIgnoreCase(valor); }

    public static Long id(ResultSet rs, String col) throws SQLException {
        long v = rs.getLong(col);
        return rs.wasNull() ? null : v;
    }

    public static Double num(ResultSet rs, String col) throws SQLException {
        double v = rs.getDouble(col);
        return rs.wasNull() ? null : v;
    }

    public static Integer inteiro(ResultSet rs, String col) throws SQLException {
        int v = rs.getInt(col);
        return rs.wasNull() ? null : v;
    }
}
