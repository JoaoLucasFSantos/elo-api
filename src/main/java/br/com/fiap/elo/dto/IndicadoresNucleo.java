package br.com.fiap.elo.dto;

import java.util.List;
import java.util.Map;

public record IndicadoresNucleo(Long idNucleo, String nomeNucleo, Double cargaTotalHoras,
                                Double concentracaoMaiorCargaPercentual,
                                Map<String, Integer> atribuicoesPorStatus,
                                List<IndicadorParticipante> participantes) {}
