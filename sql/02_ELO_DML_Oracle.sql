/* ELO - Dados totalmente FICTICIOS, utilizados exclusivamente para demonstracao academica.
   Execute depois do arquivo 01_ELO_DDL_Oracle.sql. Oracle 19c. */

INSERT INTO TB_NUCLEO_CUIDADO VALUES (1, 'Familia Oliveira', 'Cuidado compartilhado de Lucia Oliveira.', 'Sao Paulo', 'SP', DATE '2026-08-01');
INSERT INTO TB_NUCLEO_CUIDADO VALUES (2, 'Familia Santos', 'Cuidado compartilhado de Joao Santos.', 'Guarulhos', 'SP', DATE '2026-08-02');
INSERT INTO TB_NUCLEO_CUIDADO VALUES (3, 'Familia Lima', 'Cuidado compartilhado de Ana Lima.', 'Campinas', 'SP', DATE '2026-08-03');
INSERT INTO TB_NUCLEO_CUIDADO VALUES (4, 'Familia Costa', 'Cuidado compartilhado de Pedro Costa.', 'Sao Bernardo do Campo', 'SP', DATE '2026-08-04');
INSERT INTO TB_NUCLEO_CUIDADO VALUES (5, 'Familia Pereira', 'Cuidado compartilhado de Helena Pereira.', 'Osasco', 'SP', DATE '2026-08-05');

INSERT INTO TB_USUARIO VALUES (1, 'Mariana Oliveira', 'mariana.oliveira@example.com', '11900001001', 'CUIDADOR', 'S', 'Sao Paulo', 'SP');
INSERT INTO TB_USUARIO VALUES (2, 'Rafael Oliveira', 'rafael.oliveira@example.com', '11900001002', 'FAMILIAR', 'S', 'Sao Paulo', 'SP');
INSERT INTO TB_USUARIO VALUES (3, 'Juliana Santos', 'juliana.santos@example.com', '11900001003', 'CUIDADOR', 'S', 'Guarulhos', 'SP');
INSERT INTO TB_USUARIO VALUES (4, 'Beatriz Lima', 'beatriz.lima@example.com', '19900001004', 'CUIDADOR', 'N', 'Campinas', 'SP');
INSERT INTO TB_USUARIO VALUES (5, 'Carlos Costa', 'carlos.costa@example.com', '11900001005', 'FAMILIAR', 'S', 'Sao Bernardo do Campo', 'SP');
INSERT INTO TB_USUARIO VALUES (6, 'Fernanda Pereira', 'fernanda.pereira@example.com', '11900001006', 'CUIDADOR', 'S', 'Osasco', 'SP');

INSERT INTO TB_PARTICIPANTE_NUCLEO VALUES (1, 1, 1, 'CUIDADOR_PRINCIPAL', 25, 32, 'S');
INSERT INTO TB_PARTICIPANTE_NUCLEO VALUES (2, 1, 2, 'FAMILIAR_COLABORADOR', 10, 6, 'S');
INSERT INTO TB_PARTICIPANTE_NUCLEO VALUES (3, 2, 3, 'CUIDADOR_PRINCIPAL', 30, 28, 'S');
INSERT INTO TB_PARTICIPANTE_NUCLEO VALUES (4, 3, 4, 'CUIDADOR_PRINCIPAL', 20, 23, 'S');
INSERT INTO TB_PARTICIPANTE_NUCLEO VALUES (5, 4, 5, 'FAMILIAR_COLABORADOR', 12, 8, 'S');
INSERT INTO TB_PARTICIPANTE_NUCLEO VALUES (6, 5, 6, 'CUIDADOR_PRINCIPAL', 20, 22, 'S');

INSERT INTO TB_PESSOA_CUIDADA VALUES (1, 1, 'Lucia Oliveira', DATE '1948-04-12', 'ALTA', 'Necessita de apoio para mobilidade.', 'Contato familiar 01');
INSERT INTO TB_PESSOA_CUIDADA VALUES (2, 2, 'Joao Santos', DATE '1952-09-20', 'MEDIA', 'Acompanhamento em terapias.', 'Contato familiar 02');
INSERT INTO TB_PESSOA_CUIDADA VALUES (3, 3, 'Ana Lima', DATE '1945-01-05', 'ALTA', 'Ajuda em atividades cotidianas.', 'Contato familiar 03');
INSERT INTO TB_PESSOA_CUIDADA VALUES (4, 4, 'Pedro Costa', DATE '1960-11-30', 'MEDIA', 'Em processo de reabilitacao.', 'Contato familiar 04');
INSERT INTO TB_PESSOA_CUIDADA VALUES (5, 5, 'Helena Pereira', DATE '1950-06-14', 'BAIXA', 'Acompanhamento ocasional.', 'Contato familiar 05');

INSERT INTO TB_TAREFA_CUIDADO VALUES (1, 1, 1, 'Organizar medicamentos prescritos', 'MEDICACAO', 'DIARIA', 20, 'ALTA');
INSERT INTO TB_TAREFA_CUIDADO VALUES (2, 1, 1, 'Acompanhar consulta', 'CONSULTA', 'MENSAL', 180, 'ALTA');
INSERT INTO TB_TAREFA_CUIDADO VALUES (3, 2, 2, 'Comprar insumos de higiene', 'COMPRAS', 'SEMANAL', 60, 'MEDIA');
INSERT INTO TB_TAREFA_CUIDADO VALUES (4, 3, 3, 'Auxiliar alimentacao', 'ALIMENTACAO', 'DIARIA', 80, 'ALTA');
INSERT INTO TB_TAREFA_CUIDADO VALUES (5, 4, 4, 'Levar para fisioterapia', 'TERAPIA', 'SEMANAL', 120, 'ALTA');
INSERT INTO TB_TAREFA_CUIDADO VALUES (6, 5, 5, 'Organizar refeicoes', 'ALIMENTACAO', 'DIARIA', 90, 'MEDIA');
INSERT INTO TB_TAREFA_CUIDADO VALUES (7, 1, 1, 'Realizar compras semanais', 'COMPRAS', 'SEMANAL', 60, 'BAIXA');
INSERT INTO TB_TAREFA_CUIDADO VALUES (8, 2, 2, 'Acompanhar caminhada assistida', 'ATIVIDADE', 'SEMANAL', 45, 'MEDIA');

INSERT INTO TB_ATRIBUICAO_TAREFA VALUES (1, 1, 1, 1, DATE '2026-11-12', 'CONCLUIDA', DATE '2026-11-12', 'Organizacao registrada pela familia.');
INSERT INTO TB_ATRIBUICAO_TAREFA VALUES (2, 1, 2, 2, DATE '2026-11-20', 'PENDENTE', NULL, 'Confirmar transporte.');
INSERT INTO TB_ATRIBUICAO_TAREFA VALUES (3, 2, 3, 3, DATE '2026-11-18', 'EM_ANDAMENTO', NULL, 'Lista de compras conferida.');
INSERT INTO TB_ATRIBUICAO_TAREFA VALUES (4, 3, 4, 4, DATE '2026-11-14', 'CONCLUIDA', DATE '2026-11-14', 'Registro de apoio.');
INSERT INTO TB_ATRIBUICAO_TAREFA VALUES (5, 4, 5, 5, DATE '2026-11-19', 'PENDENTE', NULL, 'Separar documentos.');
INSERT INTO TB_ATRIBUICAO_TAREFA VALUES (6, 5, 6, 6, DATE '2026-11-17', 'PENDENTE', NULL, 'Revisar lista de itens.');
INSERT INTO TB_ATRIBUICAO_TAREFA VALUES (7, 1, 7, 2, DATE '2026-11-13', 'CONCLUIDA', DATE '2026-11-13', 'Compras entregues.');
INSERT INTO TB_ATRIBUICAO_TAREFA VALUES (8, 2, 8, 3, DATE '2026-11-21', 'PENDENTE', NULL, 'Confirmar disponibilidade.');

INSERT INTO TB_EVENTO_AGENDA VALUES (1, 1, 1, 2, 'Consulta de rotina', 'CONSULTA', TO_DATE('2026-11-20 14:00','YYYY-MM-DD HH24:MI'), 'Unidade de saude ficticia A', 'AGENDADO');
INSERT INTO TB_EVENTO_AGENDA VALUES (2, 1, 1, 1, 'Retirada de medicamentos', 'MEDICAMENTO', TO_DATE('2026-11-21 09:00','YYYY-MM-DD HH24:MI'), 'Farmacia ficticia B', 'AGENDADO');
INSERT INTO TB_EVENTO_AGENDA VALUES (3, 2, 2, 3, 'Sessao de terapia', 'TERAPIA', TO_DATE('2026-11-13 10:00','YYYY-MM-DD HH24:MI'), 'Centro ficticio C', 'REALIZADO');
INSERT INTO TB_EVENTO_AGENDA VALUES (4, 4, 4, 5, 'Exame de rotina', 'EXAME', TO_DATE('2026-11-22 08:00','YYYY-MM-DD HH24:MI'), 'Laboratorio ficticio D', 'AGENDADO');
INSERT INTO TB_EVENTO_AGENDA VALUES (5, 5, 5, 6, 'Reuniao familiar de cuidado', 'COMPROMISSO', TO_DATE('2026-11-23 19:00','YYYY-MM-DD HH24:MI'), 'Residencia familiar', 'AGENDADO');

INSERT INTO TB_CHECKIN_SOBRECARGA VALUES (1, 1, DATE '2026-11-02', 5, 34, 'S', 'Muito cansaco e pouca disponibilidade para descanso.');
INSERT INTO TB_CHECKIN_SOBRECARGA VALUES (2, 1, DATE '2026-11-16', 4, 30, 'S', 'Parte das tarefas foi redistribuida.');
INSERT INTO TB_CHECKIN_SOBRECARGA VALUES (3, 2, DATE '2026-11-16', 2, 10, 'N', 'Consigo assumir outras tarefas.');
INSERT INTO TB_CHECKIN_SOBRECARGA VALUES (4, 3, DATE '2026-11-16', 4, 28, 'S', 'Dificuldade para conciliar compromissos.');
INSERT INTO TB_CHECKIN_SOBRECARGA VALUES (5, 4, DATE '2026-11-16', 5, 23, 'S', 'Necessidade de apoio adicional.');
INSERT INTO TB_CHECKIN_SOBRECARGA VALUES (6, 5, DATE '2026-11-16', 2, 8, 'N', 'Rotina controlada.');
INSERT INTO TB_CHECKIN_SOBRECARGA VALUES (7, 3, DATE '2026-11-09', 3, 26, 'N', 'Houve ajuda pontual da familia.');

INSERT INTO TB_REDE_APOIO VALUES (1, 'Centro Comunitario Horizonte', 'ONG', 'Sao Paulo', 'SP', NULL, 'Familias e cuidadores', 'Instituicao ficticia para demonstracao de rede de apoio.');
INSERT INTO TB_REDE_APOIO VALUES (2, 'Nucleo de Apoio Familia', 'SERVICO_PUBLICO', 'Guarulhos', 'SP', NULL, 'Familias vulneraveis', 'Servico ficticio para fins academicos.');
INSERT INTO TB_REDE_APOIO VALUES (3, 'Associacao Cuidado Solidario', 'ONG', 'Campinas', 'SP', NULL, 'Pessoas idosas', 'Instituicao ficticia para fins academicos.');
INSERT INTO TB_REDE_APOIO VALUES (4, 'Rede Comunitaria Acolher', 'INSTITUICAO_COMUNITARIA', 'Sao Bernardo do Campo', 'SP', NULL, 'Cuidadores familiares', 'Instituicao ficticia para fins academicos.');
INSERT INTO TB_REDE_APOIO VALUES (5, 'Projeto Apoio em Rede', 'ONG', 'Osasco', 'SP', NULL, 'Idosos e familiares', 'Instituicao ficticia para fins academicos.');

INSERT INTO TB_ENCAMINHAMENTO_APOIO VALUES (1, 1, 1, DATE '2026-11-01', 'CONTATADO', 'Familia solicitou informacoes.');
INSERT INTO TB_ENCAMINHAMENTO_APOIO VALUES (2, 2, 2, DATE '2026-11-02', 'SUGERIDO', 'Indicacao para orientacao social.');
INSERT INTO TB_ENCAMINHAMENTO_APOIO VALUES (3, 3, 3, DATE '2026-11-03', 'ATENDIDO', 'Familia participou de encontro informativo.');
INSERT INTO TB_ENCAMINHAMENTO_APOIO VALUES (4, 4, 4, DATE '2026-11-04', 'SUGERIDO', 'Indicado grupo de apoio.');
INSERT INTO TB_ENCAMINHAMENTO_APOIO VALUES (5, 5, 5, DATE '2026-11-05', 'CONTATADO', 'Contato inicial registrado.');

COMMIT;
