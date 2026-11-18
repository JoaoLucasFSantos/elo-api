package br.com.fiap.elo.bo;

import br.com.fiap.elo.dao.UsuarioDao;
import br.com.fiap.elo.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class UsuarioBo extends BaseBo<Usuario> {

    public UsuarioBo(UsuarioDao dao) {
        super(dao, "Usuário");
    }

    @Override
    protected Usuario preparar(Usuario u, boolean criacao) {
        boolean whatsapp = Boolean.TRUE.equals(u.recebeWhatsapp());
        Regras.exigir(!whatsapp || (u.telefone() != null && !u.telefone().isBlank()),
                "Para receber lembretes pelo WhatsApp é obrigatório informar o telefone.");
        String uf = u.uf() == null ? null : u.uf().toUpperCase();
        return new Usuario(null, u.nome().trim(), u.email().trim().toLowerCase(), u.telefone(),
                u.perfilPrincipal().toUpperCase(), whatsapp, u.cidade(), uf);
    }
}
