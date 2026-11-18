package br.com.fiap.elo.dao;

import br.com.fiap.elo.model.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/** Acesso a dados da tabela TB_USUARIO (JDBC via JdbcTemplate). */
@Repository
public class UsuarioDao extends BaseDao<Usuario> {

    public UsuarioDao(JdbcTemplate jdbc) {
        super(jdbc, "TB_USUARIO", "ID_USUARIO", UsuarioDao::mapear);
    }

    private static Usuario mapear(ResultSet rs, int linha) throws SQLException {
        return new Usuario(
                Conv.id(rs, "ID_USUARIO"),
                rs.getString("NOME_COMPLETO"),
                rs.getString("EMAIL"),
                rs.getString("TELEFONE"),
                rs.getString("PERFIL_PRINCIPAL"),
                Conv.deSN(rs.getString("RECEBE_WHATSAPP")),
                rs.getString("CIDADE"),
                rs.getString("UF"));
    }

    @Override
    public Usuario inserir(Usuario o) {
        Long id = proximoId("SEQ_USUARIO");
        jdbc.update("INSERT INTO TB_USUARIO (ID_USUARIO, NOME_COMPLETO, EMAIL, TELEFONE, PERFIL_PRINCIPAL, RECEBE_WHATSAPP, CIDADE, UF) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                id, o.nome(), o.email(), o.telefone(), o.perfilPrincipal(), Conv.paraSN(o.recebeWhatsapp(), false), o.cidade(), o.uf());
        return buscarPorId(id).orElseThrow();
    }

    @Override
    public boolean atualizar(Long id, Usuario o) {
        return jdbc.update("UPDATE TB_USUARIO SET NOME_COMPLETO = ?, EMAIL = ?, TELEFONE = ?, PERFIL_PRINCIPAL = ?, RECEBE_WHATSAPP = ?, CIDADE = ?, UF = ? WHERE ID_USUARIO = ?",
                o.nome(), o.email(), o.telefone(), o.perfilPrincipal(), Conv.paraSN(o.recebeWhatsapp(), false), o.cidade(), o.uf(), id) > 0;
    }
}
