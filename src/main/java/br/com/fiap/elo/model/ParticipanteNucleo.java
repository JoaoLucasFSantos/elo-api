package br.com.fiap.elo.model;

import jakarta.validation.constraints.*;
import java.time.*;

/** Espelha a tabela TB_PARTICIPANTE_NUCLEO. */
public record ParticipanteNucleo(
        Long id,
        @NotNull Long idNucleo,
        @NotNull Long idUsuario,
        @NotBlank @Pattern(regexp = "CUIDADOR_PRINCIPAL|FAMILIAR_COLABORADOR", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Valores aceitos: CUIDADOR_PRINCIPAL, FAMILIAR_COLABORADOR") String papel,
        @NotNull @PositiveOrZero Double disponibilidadeHorasSemana,
        @NotNull @PositiveOrZero Double cargaAtualHoras,
        Boolean ativo) {}
