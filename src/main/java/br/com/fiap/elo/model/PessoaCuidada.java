package br.com.fiap.elo.model;

import jakarta.validation.constraints.*;
import java.time.*;

/** Espelha a tabela TB_PESSOA_CUIDADA. */
public record PessoaCuidada(
        Long id,
        @NotNull Long idNucleo,
        @NotBlank @Size(max = 120) String nome,
        @NotNull @Past LocalDate dataNascimento,
        @NotBlank @Pattern(regexp = "BAIXA|MEDIA|ALTA", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Valores aceitos: BAIXA, MEDIA, ALTA") String grauDependencia,
        @Size(max = 300) String observacoes,
        @Size(max = 100) String contatoEmergencia) {}
