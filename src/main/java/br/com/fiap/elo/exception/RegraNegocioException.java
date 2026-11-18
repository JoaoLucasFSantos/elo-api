package br.com.fiap.elo.exception;

/** Lançada quando uma regra de negócio do Elo é violada (HTTP 422). */
public class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
