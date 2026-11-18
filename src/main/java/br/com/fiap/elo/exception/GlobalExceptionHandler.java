package br.com.fiap.elo.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;

/** Camada de tratamento de exceções: converte erros em respostas JSON padronizadas. */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ApiError> naoEncontrado(RecursoNaoEncontradoException ex, HttpServletRequest req) {
        return erro(HttpStatus.NOT_FOUND, ex.getMessage(), req, List.of());
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ApiError> regraNegocio(RegraNegocioException ex, HttpServletRequest req) {
        return erro(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), req, List.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> validacao(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<String> detalhes = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage()).toList();
        return erro(HttpStatus.BAD_REQUEST, "Dados inválidos.", req, detalhes);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiError> requisicaoInvalida(Exception ex, HttpServletRequest req) {
        return erro(HttpStatus.BAD_REQUEST,
                "Requisição inválida: verifique tipos, valores e formato das datas "
                        + "(yyyy-MM-dd ou yyyy-MM-dd'T'HH:mm:ss).", req, List.of());
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiError> banco(DataAccessException ex, HttpServletRequest req) {
        String causa = NestedExceptionUtils.getMostSpecificCause(ex).getMessage();
        causa = causa == null ? "" : causa;
        if (causa.contains("ORA-02292")) {
            return erro(HttpStatus.CONFLICT,
                    "Não é possível excluir ou alterar: existem registros vinculados a este item.", req, List.of());
        }
        if (causa.contains("ORA-02291")) {
            return erro(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Referência inválida: o registro relacionado não existe ou pertence a outro núcleo.",
                    req, List.of());
        }
        if (causa.contains("ORA-00001")) {
            return erro(HttpStatus.CONFLICT, "Já existe um registro com este valor (campo único).", req, List.of());
        }
        if (causa.contains("ORA-02290") || causa.contains("ORA-01400")) {
            return erro(HttpStatus.BAD_REQUEST, "Os dados informados violam uma regra de integridade do banco.",
                    req, List.of());
        }
        log.error("Erro de acesso ao banco em {}", req.getRequestURI(), ex);
        return erro(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao acessar o banco de dados.", req, List.of());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> generico(Exception ex, HttpServletRequest req) {
        if (ex instanceof ErrorResponse er) { // 404 de rota, 405 de método etc. mantêm o status original
            return erro(HttpStatus.valueOf(er.getStatusCode().value()), ex.getMessage(), req, List.of());
        }
        log.error("Erro inesperado em {}", req.getRequestURI(), ex);
        return erro(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno inesperado.", req, List.of());
    }

    private ResponseEntity<ApiError> erro(HttpStatus status, String mensagem, HttpServletRequest req,
                                          List<String> detalhes) {
        ApiError corpo = new ApiError(LocalDateTime.now(), status.value(), status.getReasonPhrase(),
                mensagem, req.getRequestURI(), detalhes);
        return ResponseEntity.status(status).body(corpo);
    }
}
