package br.com.fiap.elo.model;

import jakarta.validation.constraints.*;
import java.time.*;

/** Espelha a tabela TB_NUCLEO_CUIDADO. */
public record NucleoCuidado(
        Long id,
        @NotBlank @Size(max = 100) String nome,
        @Size(max = 300) String descricao,
        @NotBlank @Size(max = 60) String cidade,
        @NotBlank @Pattern(regexp = "[A-Za-z]{2}", message = "UF deve ter 2 letras") String uf,
        LocalDate dataCriacao) {}
