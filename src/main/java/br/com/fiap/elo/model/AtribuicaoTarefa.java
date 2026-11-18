package br.com.fiap.elo.model;

import jakarta.validation.constraints.*;
import java.time.*;

/** Espelha a tabela TB_ATRIBUICAO_TAREFA. */
public record AtribuicaoTarefa(
        Long id,
        @NotNull Long idNucleo,
        @NotNull Long idTarefa,
        @NotNull Long idParticipante,
        @NotNull LocalDate dataPrevista,
        @Pattern(regexp = "PENDENTE|EM_ANDAMENTO|CONCLUIDA", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Valores aceitos: PENDENTE, EM_ANDAMENTO, CONCLUIDA") String status,
        LocalDate dataConclusao,
        @Size(max = 300) String observacao) {}
