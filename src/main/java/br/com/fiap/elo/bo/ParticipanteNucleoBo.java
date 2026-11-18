package br.com.fiap.elo.bo;

import br.com.fiap.elo.dao.NucleoCuidadoDao;
import br.com.fiap.elo.dao.ParticipanteNucleoDao;
import br.com.fiap.elo.dao.UsuarioDao;
import br.com.fiap.elo.model.ParticipanteNucleo;
import org.springframework.stereotype.Service;

@Service
public class ParticipanteNucleoBo extends BaseBo<ParticipanteNucleo> {

    private final NucleoCuidadoDao nucleoDao;
    private final UsuarioDao usuarioDao;

    public ParticipanteNucleoBo(ParticipanteNucleoDao dao, NucleoCuidadoDao nucleoDao, UsuarioDao usuarioDao) {
        super(dao, "Participante");
        this.nucleoDao = nucleoDao;
        this.usuarioDao = usuarioDao;
    }

    @Override
    protected ParticipanteNucleo preparar(ParticipanteNucleo p, boolean criacao) {
        Regras.existe(nucleoDao.buscarPorId(p.idNucleo()), "O núcleo " + p.idNucleo() + " não existe.");
        Regras.existe(usuarioDao.buscarPorId(p.idUsuario()), "O usuário " + p.idUsuario() + " não existe.");
        boolean ativo = p.ativo() == null || p.ativo();
        return new ParticipanteNucleo(null, p.idNucleo(), p.idUsuario(), p.papel().toUpperCase(),
                p.disponibilidadeHorasSemana(), p.cargaAtualHoras(), ativo);
    }
}
