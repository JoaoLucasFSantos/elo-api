package br.com.fiap.elo.model;

import jakarta.validation.constraints.*;
import java.time.*;

/** Espelha a tabela TB_ENCAMINHAMENTO_APOIO. */
public record EncaminhamentoApoio(
        Long id,
        @NotNull Long idNucleo,
        @NotNull Long idApoio,
        LocalDate dataEncaminhamento,
        @Pattern(regexp = "SUGERIDO|CONTATADO|ATENDIDO", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Valores aceitos: SUGERIDO, CONTATADO, ATENDIDO") String status,
        @Size(max = 300) String observacao) {}
