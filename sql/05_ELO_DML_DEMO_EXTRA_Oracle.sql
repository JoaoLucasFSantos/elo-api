/*
 ELO - Dados fictícios ADICIONAIS (opcional) para demonstrar a redistribuição de tarefas.
 Sem eles, nenhum participante sobrecarregado tem tarefas em aberto para mover.
 Executar depois de 01, 02 e 04.
*/
INSERT INTO TB_USUARIO VALUES (7, 'Paulo Oliveira', 'paulo.oliveira@example.com', '11900001007', 'FAMILIAR', 'S', 'Sao Paulo', 'SP');
INSERT INTO TB_USUARIO VALUES (8, 'Lucas Lima', 'lucas.lima@example.com', '19900001008', 'FAMILIAR', 'S', 'Campinas', 'SP');

INSERT INTO TB_PARTICIPANTE_NUCLEO VALUES (7, 1, 7, 'FAMILIAR_COLABORADOR', 15, 3, 'S');
INSERT INTO TB_PARTICIPANTE_NUCLEO VALUES (8, 3, 8, 'FAMILIAR_COLABORADOR', 10, 2, 'S');

INSERT INTO TB_TAREFA_CUIDADO VALUES (9, 1, 1, 'Auxiliar no banho', 'HIGIENE', 'DIARIA', 45, 'ALTA');
INSERT INTO TB_TAREFA_CUIDADO VALUES (10, 1, 1, 'Preparar refeicoes', 'ALIMENTACAO', 'DIARIA', 60, 'MEDIA');
INSERT INTO TB_TAREFA_CUIDADO VALUES (11, 3, 3, 'Auxiliar na higiene', 'HIGIENE', 'DIARIA', 30, 'MEDIA');

INSERT INTO TB_ATRIBUICAO_TAREFA VALUES (9, 1, 9, 1, DATE '2026-11-18', 'PENDENTE', NULL, 'Rotina diaria da manha.');
INSERT INTO TB_ATRIBUICAO_TAREFA VALUES (10, 1, 10, 1, DATE '2026-11-18', 'PENDENTE', NULL, 'Rotina diaria.');
INSERT INTO TB_ATRIBUICAO_TAREFA VALUES (11, 3, 11, 4, DATE '2026-11-18', 'PENDENTE', NULL, 'Rotina diaria.');

INSERT INTO TB_CHECKIN_SOBRECARGA VALUES (8, 7, DATE '2026-11-16', 1, 3, 'N', 'Posso ajudar mais.');

COMMIT;
