/* ELO - Consultas solicitadas no enunciado da Global Solution - Database Design. */

/* 1) INNER JOIN (2 tabelas)
   Objetivo: listar as pessoas cuidadas com os seus nucleos familiares. */
SELECT
    P.ID_PESSOA_CUIDADA,
    P.NOME_COMPLETO AS PESSOA_CUIDADA,
    P.GRAU_DEPENDENCIA,
    N.NOME_NUCLEO,
    N.CIDADE
FROM TB_PESSOA_CUIDADA P
INNER JOIN TB_NUCLEO_CUIDADO N
    ON N.ID_NUCLEO = P.ID_NUCLEO
ORDER BY N.NOME_NUCLEO, P.NOME_COMPLETO;

/* 2) INNER JOIN + funcao de grupo + GROUP BY (2 tabelas)
   Objetivo: medir o numero de atribuicoes e o tempo previsto, por categoria de tarefa. */
SELECT
    T.CATEGORIA,
    COUNT(A.ID_ATRIBUICAO) AS QT_ATRIBUICOES,
    SUM(T.TEMPO_ESTIMADO_MIN) AS TOTAL_MINUTOS_PREVISTOS
FROM TB_TAREFA_CUIDADO T
INNER JOIN TB_ATRIBUICAO_TAREFA A
    ON A.ID_TAREFA = T.ID_TAREFA
GROUP BY T.CATEGORIA
ORDER BY TOTAL_MINUTOS_PREVISTOS DESC, T.CATEGORIA;

/* 3) LEFT OUTER JOIN + WHERE + ORDER BY (2 tabelas)
   Objetivo: listar participantes ativos mesmo sem check-in, incluindo avaliacao
   de sobrecarga quando houver. Note que o filtro WHERE usa a tabela da esquerda
   para preservar quem ainda nao respondeu ao questionario. */
SELECT
    P.ID_PARTICIPANTE,
    P.ID_NUCLEO,
    P.PAPEL,
    C.DATA_CHECKIN,
    C.NIVEL_SOBRECARGA,
    C.HORAS_CUIDADO_SEMANA,
    C.PRECISOU_AJUDA
FROM TB_PARTICIPANTE_NUCLEO P
LEFT OUTER JOIN TB_CHECKIN_SOBRECARGA C
    ON C.ID_PARTICIPANTE = P.ID_PARTICIPANTE
WHERE P.ATIVO = 'S'
ORDER BY C.NIVEL_SOBRECARGA DESC NULLS LAST, P.ID_PARTICIPANTE;
