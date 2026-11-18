package br.com.fiap.elo.model;

import jakarta.validation.constraints.*;
import java.time.*;

/** Espelha a tabela TB_EVENTO_AGENDA. */
public record EventoAgenda(
        Long id,
        @NotNull Long idNucleo,
        @NotNull Long idPessoaCuidada,
        Long idParticipanteResp,
        @NotBlank @Size(max = 120) String titulo,
        @NotBlank @Pattern(regexp = "CONSULTA|EXAME|MEDICAMENTO|TERAPIA|COMPROMISSO", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Valores aceitos: CONSULTA, EXAME, MEDICAMENTO, TERAPIA, COMPROMISSO") String tipoEvento,
        @NotNull LocalDateTime dataEvento,
        @Size(max = 120) String localEvento,
        @Pattern(regexp = "AGENDADO|REALIZADO|CANCELADO", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Valores aceitos: AGENDADO, REALIZADO, CANCELADO") String statusEvento) {}
