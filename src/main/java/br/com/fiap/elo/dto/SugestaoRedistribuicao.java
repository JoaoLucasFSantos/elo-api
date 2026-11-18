package br.com.fiap.elo.dto;

public record SugestaoRedistribuicao(Long idAtribuicao, String tarefa, Long deParticipante, String deNome,
                                     Long paraParticipante, String paraNome, Double horasSemanais,
                                     String motivo) {}
