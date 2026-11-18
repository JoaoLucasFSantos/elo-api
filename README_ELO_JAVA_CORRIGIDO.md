# Elo — Cuidando de Quem Cuida | API Java

Projeto da Global Solution 2026.2 — FIAP / Engenharia de Software / CitizenTech.

Esta API permite cadastrar famílias, pessoas cuidadas, tarefas, responsáveis, compromissos, check-ins de sobrecarga e redes de apoio. Foi desenvolvida com **Java 21**, **Spring Boot 3.3.5**, **Maven** e **Oracle Database**, usando camadas `model`, `dao`, `bo`, `controller` e `exception`.

## 1. O que instalar

- **JDK 21** (`java -version` deve mostrar a versão 21).
- **Apache Maven 3.9 ou superior** (`mvn -version`).
- **Oracle SQL Developer** para preparar e verificar o banco, com acesso a uma conta Oracle válida. Não é necessário ter o Oracle Database instalado localmente se estiver usando o servidor da FIAP.
- Uma IDE, como VS Code ou IntelliJ IDEA, é opcional.

## 2. Preparar o banco Oracle

A conexão utilizada nos testes pela equipe foi:

| Campo | Valor |
|---|---|
| Host | `oracle.fiap.com.br` |
| Porta | `1521` |
| Tipo | `SID` |
| SID | `ORCL` |
| Usuário e senha | Credenciais Oracle do próprio aluno ou avaliador |

No **Oracle SQL Developer**, conecte ao esquema Oracle onde a API vai operar. **Em um esquema vazio**, abra os arquivos da pasta `sql` e execute cada script completo com **F5**, nesta ordem:

1. `01_ELO_DDL_Oracle.sql` — cria as 10 tabelas e restrições.
2. `02_ELO_DML_Oracle.sql` — insere os 60 registros fictícios iniciais.
3. `04_ELO_SEQUENCES_Oracle.sql` — cria 10 sequences para gerar IDs dos novos registros feitos pela API.

O arquivo `03_ELO_DQL_Oracle.sql` contém consultas de demonstração e **não é necessário para iniciar a API**. O `05_ELO_DML_DEMO_EXTRA_Oracle.sql` é **opcional**, usado para demonstrar cenários adicionais de redistribuição de tarefas; execute-o uma única vez e só se desejar esses registros extras.

**Atenção:** se já houver tabelas e sequences do Elo no seu esquema (como ocorreu nos testes da equipe), **não execute novamente** os scripts 01, 02 ou 04. Isso causará erros de objeto existente, chave duplicada ou inserções repetidas.

Para conferir a estrutura existente, execute no SQL Developer:

```sql
SELECT COUNT(*) AS TOTAL_TABELAS
FROM USER_TABLES
WHERE TABLE_NAME IN (
  'TB_NUCLEO_CUIDADO', 'TB_USUARIO', 'TB_PARTICIPANTE_NUCLEO',
  'TB_PESSOA_CUIDADA', 'TB_TAREFA_CUIDADO', 'TB_ATRIBUICAO_TAREFA',
  'TB_EVENTO_AGENDA', 'TB_CHECKIN_SOBRECARGA', 'TB_REDE_APOIO',
  'TB_ENCAMINHAMENTO_APOIO'
);

SELECT COUNT(*) AS TOTAL_SEQUENCES
FROM USER_SEQUENCES
WHERE SEQUENCE_NAME IN (
  'SEQ_NUCLEO', 'SEQ_USUARIO', 'SEQ_PARTICIPANTE', 'SEQ_PESSOA',
  'SEQ_TAREFA', 'SEQ_ATRIBUICAO', 'SEQ_EVENTO', 'SEQ_CHECKIN',
  'SEQ_APOIO', 'SEQ_ENCAMINHAMENTO'
);

SELECT COUNT(*) AS TOTAL_USUARIOS FROM TB_USUARIO;
```

Em um esquema recém-preparado, os resultados esperados são **10 tabelas, 10 sequences e 6 usuários fictícios**. Se houver dados adicionados por testes, a quantidade de usuários poderá ser maior.

## 3. Configurar acesso ao banco no computador

**Não coloque usuário nem senha diretamente no `application.properties`, no código ou no GitHub.** O projeto lê variáveis de ambiente definidas apenas no terminal onde ele será iniciado.

No **PowerShell** do Windows, na pasta que contém `pom.xml`, execute:

```powershell
$env:DB_URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL"
$env:DB_USER = "SEU_USUARIO_ORACLE"
$senha = Read-Host "Senha do Oracle" -AsSecureString
$env:DB_PASSWORD = [System.Net.NetworkCredential]::new("", $senha).Password
```

Substitua `SEU_USUARIO_ORACLE` pelo usuário do esquema conectado no SQL Developer. A senha será solicitada sem aparecer no terminal. **Não compartilhe essa senha com colegas nem inclua sua senha pessoal no trabalho.**

Se a instituição fornecer outro servidor, porta ou SID, ajuste `DB_URL` conforme os dados da sua conexão.

No **macOS/Linux**, o equivalente é:

```bash
export DB_URL='jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL'
export DB_USER='SEU_USUARIO_ORACLE'
read -rs -p 'Senha do Oracle: ' DB_PASSWORD; echo
export DB_PASSWORD
```

## 4. Compilar e iniciar

Abra o terminal **na pasta do projeto que contém `pom.xml`** e execute:

```bash
mvn clean package "-DskipTests"
mvn spring-boot:run
```

O primeiro comando deve terminar em `BUILD SUCCESS`. O segundo inicia o servidor Spring Boot e **permanece aberto** enquanto a API estiver rodando. A API normalmente utiliza a porta `8080` (variável `PORT` pode alterar isso). Para parar, pressione `Ctrl + C`.

Use **o mesmo terminal** onde definiu `DB_URL`, `DB_USER` e `DB_PASSWORD`, ou configure as variáveis novamente em uma nova sessão.

## 5. Testar no navegador / Swagger

Com a aplicação em execução:

- API: `http://localhost:8080/api/usuarios`
- Swagger (documentação e testes dos endpoints): `http://localhost:8080/swagger-ui.html`
- Descrição OpenAPI: `http://localhost:8080/v3/api-docs`

O primeiro endereço deve retornar um JSON com os usuários fictícios existentes no banco. Se a base inicial foi carregada, aparecem **6 usuários**, antes de novos testes.

Para listar usuários pelo PowerShell (em **outro terminal**, sem parar a API):

```powershell
Invoke-RestMethod -Uri 'http://localhost:8080/api/usuarios' |
    Format-Table id, nome, email, perfilPrincipal, cidade -AutoSize
```

### Teste de cadastro (POST)

No Swagger, abra **POST `/api/usuarios`**, clique em *Try it out* e use:

```json
{
  "nome": "Teste Integracao Elo",
  "email": "teste.integracao.unico@example.com",
  "telefone": "11999990000",
  "perfilPrincipal": "CUIDADOR",
  "recebeWhatsapp": false,
  "cidade": "Sao Paulo",
  "uf": "SP"
}
```

Não informe `id`: o sistema usa `SEQ_USUARIO` para gerar esse valor. O esperado é **201 Created** e um JSON com o novo ID. Use um e-mail não cadastrado; repetir o mesmo e-mail retorna **409 Conflict**, devido à regra de unicidade.

Com o ID criado, é possível testar **GET** `/api/usuarios/{id}`, **PUT** `/api/usuarios/{id}` e **DELETE** `/api/usuarios/{id}` no Swagger. O PUT deve retornar **200 OK** e o DELETE normalmente retorna **204 No Content**. Use apenas usuários de teste para evitar alterar os registros demonstrativos.

## 6. Principais módulos

O Swagger lista os endpoints de todos os módulos:

| Recurso | Caminho base |
|---|---|
| Núcleos de cuidado | `/api/nucleos` |
| Usuários | `/api/usuarios` |
| Participantes do núcleo | `/api/participantes` |
| Pessoas cuidadas | `/api/pessoas-cuidadas` |
| Tarefas | `/api/tarefas` |
| Atribuições | `/api/atribuicoes` |
| Eventos de agenda | `/api/eventos` |
| Check-ins de sobrecarga | `/api/checkins` |
| Rede de apoio | `/api/rede-apoio` |
| Encaminhamentos | `/api/encaminhamentos` |

Algumas listagens aceitam filtros como `idNucleo` ou `idParticipante`. Consulte o Swagger para os parâmetros e JSONs aceitos por cada rota.

Há ainda operações específicas, entre elas:

- `GET /api/nucleos/{id}/indicadores`
- `GET /api/nucleos/{id}/sugestoes-redistribuicao`
- `PUT /api/atribuicoes/{id}/reatribuir/{idParticipanteDestino}`
- `GET /api/nucleos/{id}/rede-apoio-sugerida`

## 7. Problemas comuns

| Erro | Causa mais comum / solução |
|---|---|
| `ORA-01017` | Usuário/senha incorretos ou variáveis `DB_USER` e `DB_PASSWORD` não configuradas |
| `ORA-17868` | Host de `DB_URL` incorreto; não usar `HOST_FIAP` literalmente |
| `ORA-12541` | Endereço, porta ou servidor indisponível; conferir conexão no SQL Developer |
| `ORA-00955` | Objeto já existe; não executar novamente o DDL/sequences no mesmo esquema |
| `409 Conflict` | E-mail ou outro campo único duplicado |
| `mvn` não reconhecido | Maven não instalado ou não configurado no PATH |
| HTTP `500` ao acessar a API | Verificar o terminal do Spring Boot e a mensagem `Caused by:` |

## 8. Deploy e entrega

O projeto inclui um `Dockerfile` para execução no **Render**. Criar um *Web Service* conectado ao repositório GitHub e escolher a implantação por **Docker**. No painel do Render, configurar `DB_URL`, `DB_USER` e `DB_PASSWORD` nas variáveis de ambiente; não usar a senha no repositório. O Dockerfile usa Java 21 e a aplicação respeita a variável `PORT` fornecida pelo servidor.

**Antes da entrega, o grupo precisa preencher e conferir:**

- **URL pública do repositório GitHub:** _inserir URL real do projeto_.
- **URL pública do deploy no Render:** _inserir URL real depois de testar_.
- **PDF de documentação da disciplina de Java:** capa, integrantes/RMs, diagrama de classes, DER, protótipos, endpoints e instruções de execução, conforme a tarefa.

**Importante:** ter um README local não substitui fazer o deploy, disponibilizar o GitHub nem produzir o PDF exigido. O acesso remoto ao Oracle da FIAP também depende da disponibilidade do servidor e das permissões da conta usada na implantação.

## 9. Testes realizados pela equipe

A equipe verificou a conexão JDBC com o Oracle da FIAP, o `GET /api/usuarios` (6 usuários fictícios iniciais), cadastro de usuário de teste via POST, atualização via PUT (HTTP 200) e exclusão via DELETE (HTTP 204). **Esses testes confirmam o CRUD de usuários em ambiente local; não substituem testes completos de todos os demais módulos nem do deploy em produção.**

---

**Observação:** o SQL de criação, os registros e as sequences estão disponíveis na pasta `sql`. Os dados fornecidos são fictícios e destinados à demonstração acadêmica.
