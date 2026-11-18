package br.com.fiap.elo.model;

import jakarta.validation.constraints.*;
import java.time.*;

/** Espelha a tabela TB_REDE_APOIO. */
public record RedeApoio(
        Long id,
        @NotBlank @Size(max = 120) String nomeInstituicao,
        @NotBlank @Size(max = 40) String tipoInstituicao,
        @NotBlank @Size(max = 60) String cidade,
        @NotBlank @Pattern(regexp = "[A-Za-z]{2}", message = "UF deve ter 2 letras") String uf,
        @Size(max = 20) String telefone,
        @Size(max = 100) String publicoAtendido,
        @Size(max = 300) String descricao) {}
