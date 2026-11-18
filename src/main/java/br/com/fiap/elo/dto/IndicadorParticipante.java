package br.com.fiap.elo.dto;

public record IndicadorParticipante(Long idParticipante, String nome, Double disponibilidadeHorasSemana,
                                    Double cargaAtualHoras, Double ocupacaoPercentual,
                                    Integer ultimoNivelSobrecarga, String situacao) {}
