package br.com.fiap.elo.model;

import jakarta.validation.constraints.*;
import java.time.*;

/** Espelha a tabela TB_USUARIO. */
public record Usuario(
        Long id,
        @NotBlank @Size(max = 120) String nome,
        @NotBlank @Email @Size(max = 120) String email,
        @Size(max = 20) String telefone,
        @NotBlank @Pattern(regexp = "CUIDADOR|FAMILIAR", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Valores aceitos: CUIDADOR, FAMILIAR") String perfilPrincipal,
        Boolean recebeWhatsapp,
        @Size(max = 60) String cidade,
        @Pattern(regexp = "[A-Za-z]{2}", message = "UF deve ter 2 letras") String uf) {}
