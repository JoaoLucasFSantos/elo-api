package br.com.fiap.elo.model;

import jakarta.validation.constraints.*;
import java.time.*;

/** Espelha a tabela TB_TAREFA_CUIDADO. */
public record TarefaCuidado(
        Long id,
        @NotNull Long idNucleo,
        @NotNull Long idPessoaCuidada,
        @NotBlank @Size(max = 120) String titulo,
        @NotBlank @Size(max = 40) String categoria,
        @NotBlank @Pattern(regexp = "DIARIA|SEMANAL|MENSAL", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Valores aceitos: DIARIA, SEMANAL, MENSAL") String frequencia,
        @NotNull @Positive @Max(99999) Integer tempoEstimadoMin,
        @NotBlank @Pattern(regexp = "BAIXA|MEDIA|ALTA", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Valores aceitos: BAIXA, MEDIA, ALTA") String prioridade) {}
