# Elo – Cuidando de Quem Cuida (API)

API RESTful em **Java 21 + Spring Boot 3.3** com banco **Oracle** (FIAP). Projeto da Global Solution 2026.2 – CitizenTech.
Arquitetura em camadas: `model` → `dao` (JDBC/JdbcTemplate) → `bo` (regras de negócio) → `controller` (REST) + `exception` (tratamento de erros).

## Como executar

1. **Banco (Oracle FIAP):** executar na ordem `sql/01_ELO_DDL_Oracle.sql`, `sql/02_ELO_DML_Oracle.sql`, `sql/04_ELO_SEQUENCES_Oracle.sql` e, opcionalmente, `sql/05_ELO_DML_DEMO_EXTRA_Oracle.sql`.
2. **Variáveis de ambiente:** `DB_URL` (padrão `jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL`), `DB_USER`, `DB_PASSWORD`.
3. **Rodar:** `mvn spring-boot:run` (JDK 21 + Maven 3.9) ou pela IDE, classe `EloApplication`.
4. **Documentação interativa:** `http://localhost:8080/swagger-ui.html`

## Deploy (Render)
Web Service com **Docker** apontando para este repositório; definir `DB_URL`, `DB_USER` e `DB_PASSWORD` em *Environment*. O Render informa a porta pela variável `PORT` (já tratada).

## Endpoints (CRUD completo: GET, POST, PUT, DELETE)
`/api/nucleos` · `/api/usuarios` · `/api/participantes?idNucleo=` · `/api/pessoas-cuidadas?idNucleo=` · `/api/tarefas?idNucleo=` · `/api/atribuicoes?idNucleo=` · `/api/eventos?idNucleo=` · `/api/checkins?idParticipante=` · `/api/rede-apoio` · `/api/encaminhamentos?idNucleo=`

Funcionalidades do Elo:
- `GET /api/nucleos/{id}/indicadores` – carga, ocupação, último check-in e situação (EQUILIBRADO / ATENCAO / SOBRECARREGADO) de cada participante.
- `GET /api/nucleos/{id}/sugestoes-redistribuicao` – tarefas em aberto que podem sair de quem está sobrecarregado.
- `PUT /api/atribuicoes/{id}/reatribuir/{idParticipanteDestino}` – aplica a redistribuição e transfere as horas semanais.
- `GET /api/nucleos/{id}/rede-apoio-sugerida` – instituições de apoio da cidade do núcleo.

## Erros
Respostas JSON padronizadas: 400 (dados inválidos), 404 (não encontrado), 409 (vínculo ou duplicidade), 422 (regra de negócio) e 500.
