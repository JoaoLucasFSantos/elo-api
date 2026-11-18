package br.com.fiap.elo.model;

import jakarta.validation.constraints.*;
import java.time.*;

/** Espelha a tabela TB_CHECKIN_SOBRECARGA. */
public record CheckinSobrecarga(
        Long id,
        @NotNull Long idParticipante,
        LocalDate dataCheckin,
        @NotNull @Min(1) @Max(5) Integer nivelSobrecarga,
        @NotNull @PositiveOrZero Double horasCuidadoSemana,
        @NotNull Boolean precisouAjuda,
        @Size(max = 300) String comentario) {}
