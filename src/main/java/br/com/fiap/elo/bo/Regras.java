package br.com.fiap.elo.bo;

import br.com.fiap.elo.exception.RegraNegocioException;

import java.util.Optional;

/** Atalhos para validar regras de negócio nos BOs. */
final class Regras {
    private Regras() {}

    static void exigir(boolean condicao, String mensagem) {
        if (!condicao) throw new RegraNegocioException(mensagem);
    }

    static <X> X existe(Optional<X> opcional, String mensagem) {
        return opcional.orElseThrow(() -> new RegraNegocioException(mensagem));
    }
}
