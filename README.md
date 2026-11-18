# ELO — Cuidando de Quem Cuida

**Global Solution 2026.2 | FIAP — Engenharia de Software | CitizenTech**

> API REST para organizar os cuidados com pessoas idosas ou dependentes, apoiar familiares e ajudar a identificar sobrecarga de cuidadores.

**Java 21** · **Spring Boot 3.3.5** · **Maven** · **Oracle Database** · **JDBC/JdbcTemplate** · **Swagger/OpenAPI** · **Docker** · **Render**

## 🔗 Acesso rápido

| Recurso | Link |
|---|---|
| **Código-fonte (GitHub)** | [github.com/Samuel-Oliveira-Silva/elo-api](https://github.com/Samuel-Oliveira-Silva/elo-api) |
| **API publicada (Render)** | [https://elo-api-sc9n.onrender.com/](https://elo-api-sc9n.onrender.com/) |
| **Documentação interativa (Swagger)** | [https://elo-api-sc9n.onrender.com/swagger-ui.html](https://elo-api-sc9n.onrender.com/swagger-ui.html) |
| **Teste de consulta de usuários (GET)** | [https://elo-api-sc9n.onrender.com/api/usuarios](https://elo-api-sc9n.onrender.com/api/usuarios) |
| **Especificação OpenAPI (JSON)** | [https://elo-api-sc9n.onrender.com/v3/api-docs](https://elo-api-sc9n.onrender.com/v3/api-docs) |

> **Para o professor:** é possível testar os endpoints de consulta diretamente pelos links acima. Para executar o projeto localmente ou testar operações de escrita em outro banco, siga o guia abaixo. A instância gratuita do Render pode entrar em repouso após inatividade, tornando o primeiro acesso mais demorado.

## 📌 Sobre o projeto

O **Elo** é uma proposta de plataforma de apoio a famílias responsáveis por pessoas que necessitam de cuidados contínuos. A API implementa a camada de backend para:

- Organizar **núcleos de cuidado** e seus participantes;
- Cadastrar **pessoas cuidadas**, tarefas e atribuições de responsabilidades;
- Registrar **eventos da agenda** e **check-ins de sobrecarga**;
- Consultar indicadores e sugestões para **redistribuição de tarefas**;
- Localizar registros de **redes de apoio e encaminhamentos**.

O projeto contém dados fictícios para demonstração acadêmica.

## 🏗️ Tecnologias e arquitetura

| Camada/tecnologia | Utilização |
|---|---|
| Java 21 + Spring Boot 3.3.5 | API REST e inicialização do servidor |
| Maven | Compilação e dependências |
| Oracle Database + driver JDBC | Persistência relacional |
| `JdbcTemplate` | Acesso SQL à base Oracle |
| Bean Validation | Validação dos dados recebidos |
| Springdoc OpenAPI / Swagger UI | Documentação e testes interativos |
| Docker | Empacotamento e execução em contêiner |
| Render | Hospedagem da API |

Organização das camadas em `src/main/java/br/com/fiap/elo/`:

```text
controller/   -> rotas HTTP e respostas REST
bo/           -> regras de negócio e validações
dao/          -> operações SQL com JdbcTemplate
model/        -> modelos do domínio
dto/          -> objetos de retorno de operações específicas
exception/    -> tratamento padronizado de erros
config/       -> configurações web
```

O projeto utiliza **10 tabelas Oracle**, seus relacionamentos e **10 sequences** para a geração de identificadores.

## 🚀 Como executar no computador

### 1. Pré-requisitos

- **JDK 21** instalado (`java -version`);
- **Apache Maven 3.9 ou superior** (`mvn -version`);
- Acesso a um **esquema Oracle** autorizado, por exemplo via Oracle SQL Developer;
- Git ou download do repositório em ZIP.

Não é necessário instalar o Oracle Database no computador se houver acesso ao servidor Oracle da FIAP.

### 2. Obter o projeto

No terminal:

```bash
git clone https://github.com/Samuel-Oliveira-Silva/elo-api.git
cd elo-api
```

Se baixar em ZIP, extraia o conteúdo e abra o terminal na pasta em que ficam **`pom.xml`** e **`Dockerfile`**.

### 3. Preparar o Oracle

Uma conexão utilizada no desenvolvimento foi:

| Configuração | Valor |
|---|---|
| Host | `oracle.fiap.com.br` |
| Porta | `1521` |
| Tipo de conexão | SID |
| SID | `ORCL` |
| Usuário e senha | Credenciais Oracle autorizadas do executor |

No **Oracle SQL Developer**, conecte ao seu esquema. Se estiver **vazio**, execute os scripts da pasta `sql/` nesta ordem (usar **F5** para executar o script completo):

1. `01_ELO_DDL_Oracle.sql` — criação de tabelas e restrições;
2. `02_ELO_DML_Oracle.sql` — inserção dos registros fictícios iniciais;
3. `04_ELO_SEQUENCES_Oracle.sql` — criação das sequences para os novos IDs.

Arquivos adicionais:

- `03_ELO_DQL_Oracle.sql`: consultas SQL de demonstração, opcionais;
- `05_ELO_DML_DEMO_EXTRA_Oracle.sql`: dados extras para cenários de demonstração, opcionais.

> **Importante:** se o seu esquema já contém as tabelas, registros e sequences do Elo, **não execute novamente** os scripts de criação/inserção. Execute a preparação apenas em um esquema apropriado, para evitar objetos duplicados e conflitos de chave.

Consulta para verificar as tabelas e sequences:

```sql
SELECT COUNT(*) AS TOTAL_TABELAS
FROM USER_TABLES
WHERE TABLE_NAME LIKE 'TB_%';

SELECT COUNT(*) AS TOTAL_SEQUENCES
FROM USER_SEQUENCES
WHERE SEQUENCE_NAME LIKE 'SEQ_%';

SELECT COUNT(*) AS TOTAL_USUARIOS FROM TB_USUARIO;
```

As duas primeiras consultas acima contam também objetos de outros projetos existentes no mesmo esquema. Em um esquema exclusivo recém-preparado para o Elo, são esperadas **10 tabelas**, **10 sequences** e **6 usuários fictícios** iniciais.

### 4. Configurar as variáveis de ambiente

A aplicação lê `DB_URL`, `DB_USER` e `DB_PASSWORD` do ambiente. **Nunca publique sua senha no GitHub, em arquivos de configuração versionados ou em prints.**

**Windows — PowerShell:**

```powershell
$env:DB_URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL"
$env:DB_USER = "SEU_USUARIO_ORACLE"
$senha = Read-Host "Senha Oracle" -AsSecureString
$env:DB_PASSWORD = [System.Net.NetworkCredential]::new("", $senha).Password
```

Substitua `SEU_USUARIO_ORACLE` pelo usuário real do seu esquema (por exemplo, o RM fornecido pela FIAP). Os comandos devem ser executados **no mesmo terminal** em que a aplicação será iniciada.

**macOS/Linux — Bash:**

```bash
export DB_URL='jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL'
export DB_USER='SEU_USUARIO_ORACLE'
read -rs -p 'Senha Oracle: ' DB_PASSWORD; echo
export DB_PASSWORD
```

Caso o avaliador utilize outro servidor Oracle, basta ajustar `DB_URL` e utilizar credenciais autorizadas.

### 5. Iniciar a API

Na raiz do projeto:

```bash
mvn clean package -DskipTests
mvn spring-boot:run
```

Após a inicialização, acessar:

- **API local:** http://localhost:8080/
- **Usuários (GET):** http://localhost:8080/api/usuarios
- **Swagger local:** http://localhost:8080/swagger-ui.html

Deixe o terminal com o Spring Boot aberto. Para interromper o servidor, pressione `Ctrl + C`.

## 🧪 Testar a API

### Teste 1 — GET (consulta)

A consulta pode ser feita no navegador, pelo Swagger ou pelo terminal:

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/usuarios" |
    Format-Table id, nome, email, perfilPrincipal, cidade -AutoSize
```

Na versão publicada, o endereço correspondente é:

**https://elo-api-sc9n.onrender.com/api/usuarios**

### Teste 2 — POST (cadastro)

No Swagger, abra `POST /api/usuarios`, clique em **Try it out** e envie um JSON como este:

```json
{
  "nome": "Usuario Exemplo Elo",
  "email": "usuario.exemplo.unico@example.com",
  "telefone": "11999990000",
  "perfilPrincipal": "CUIDADOR",
  "recebeWhatsapp": false,
  "cidade": "Sao Paulo",
  "uf": "SP"
}
```

O esperado é o retorno **201 Created**, com um `id` gerado automaticamente. O e-mail deve ser **único**: caso já exista, o servidor poderá responder `409 Conflict`.

### Teste 3 — GET por ID, PUT e DELETE

Use o identificador retornado no POST, **somente para um registro fictício criado para o teste**:

| Método | Endpoint | Resultado esperado |
|---|---|---|
| GET | `/api/usuarios/{id}` | `200 OK` e dados do registro |
| PUT | `/api/usuarios/{id}` | `200 OK` com dados atualizados |
| DELETE | `/api/usuarios/{id}` | `204 No Content` |
| GET após exclusão | `/api/usuarios/{id}` | `404 Not Found` |

Para PUT, o corpo deve incluir novamente os campos do usuário, com os dados alterados. O Swagger apresenta os campos e validações exigidos.

> **Atenção:** não execute POST, PUT ou DELETE na API pública sem autorização. Essas operações podem modificar os registros reais do esquema de demonstração utilizado no deploy. Prefira uma base de testes isolada.

## 📚 Endpoints disponíveis

| Módulo | Rota base |
|---|---|
| Núcleos de cuidado | `/api/nucleos` |
| Usuários | `/api/usuarios` |
| Participantes | `/api/participantes` |
| Pessoas cuidadas | `/api/pessoas-cuidadas` |
| Tarefas de cuidado | `/api/tarefas` |
| Atribuições de tarefas | `/api/atribuicoes` |
| Eventos de agenda | `/api/eventos` |
| Check-ins de sobrecarga | `/api/checkins` |
| Redes de apoio | `/api/rede-apoio` |
| Encaminhamentos | `/api/encaminhamentos` |

Os módulos possuem operações CRUD; algumas listagens aceitam filtros, como `idNucleo` ou `idParticipante`. Confira os parâmetros e os corpos JSON no [Swagger online](https://elo-api-sc9n.onrender.com/swagger-ui.html).

**Funcionalidades específicas do Elo:**

```http
GET /api/nucleos/{id}/indicadores
GET /api/nucleos/{id}/sugestoes-redistribuicao
PUT /api/atribuicoes/{id}/reatribuir/{idParticipanteDestino}
GET /api/nucleos/{id}/rede-apoio-sugerida
```

Esses endpoints permitem avaliar a distribuição de cuidados e apoiar a redistribuição de responsabilidades.

## ☁️ Deploy no Render

O deploy foi realizado por meio de um **Web Service com Docker**, conectado à branch `main` do GitHub.

| Campo no Render | Configuração |
|---|---|
| Repositório | `Samuel-Oliveira-Silva/elo-api` |
| Branch | `main` |
| Language / Runtime | `Docker` |
| Root Directory | **Vazio** (`Dockerfile` e `pom.xml` estão na raiz) |
| `DB_URL` | `jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL` |
| `DB_USER` | Usuário Oracle autorizado |
| `DB_PASSWORD` | Senha definida **somente no painel privado do Render** |

O `Dockerfile` utiliza Java 21, compila com Maven e executa a aplicação. O servidor lê a porta pela variável `PORT` quando fornecida pela hospedagem.

**Link do deploy:** [https://elo-api-sc9n.onrender.com/](https://elo-api-sc9n.onrender.com/)

**Validação registrada em 18/11/2026:** a equipe acessou o endpoint público `/api/usuarios` e visualizou os registros provenientes do banco Oracle da FIAP, confirmando o acesso da aplicação hospedada ao banco naquele momento. A disponibilidade futura depende do Render e do servidor Oracle.

## ✅ Testes realizados

- Consulta `GET /api/usuarios` em ambiente local, com retorno dos usuários fictícios cadastrados;
- Cadastro de usuário de teste por `POST`, confirmado na tabela Oracle;
- Atualização do usuário por `PUT`, com resposta HTTP `200`;
- Exclusão do usuário de teste por `DELETE`, com resposta HTTP `204`;
- Consulta pública `GET /api/usuarios` após o deploy no Render, retornando dados do Oracle.

Esses testes **não significam** que todos os endpoints ou todas as regras de negócio tenham sido validados individualmente.

## ⚠️ Erros comuns

| Mensagem/status | Orientação |
|---|---|
| `ORA-01017` | Conferir usuário e senha do Oracle |
| `ORA-17868` | Conferir host na variável `DB_URL` |
| `ORA-00955` | Algum objeto já existe; não executar scripts de criação novamente |
| `409 Conflict` | Verificar valor único duplicado, como e-mail |
| `404 Not Found` | Verificar rota e identificador solicitados |
| Erro `500` | Consultar logs do Spring Boot e mensagem de causa |
| `mvn` não reconhecido | Instalar Maven e configurar PATH |
| Render não inicia | Conferir Logs, variáveis de ambiente e `Dockerfile` |

## 🔒 Segurança e observações

- **Credenciais do Oracle são privadas** e não estão incluídas no projeto.
- Os dados demonstrativos devem ser fictícios.
- A versão acadêmica é uma demonstração: **não foi documentada autenticação/autorização para proteger operações de escrita**. Não armazenar dados pessoais reais nem expor o ambiente publicamente com informações sensíveis. Uma versão destinada a uso real deverá implementar autenticação, autorização, gestão de segredos e demais controles de segurança.
- O acesso ao Oracle da FIAP depende de disponibilidade do servidor, credenciais autorizadas e regras de rede.

---

**FIAP — Global Solution 2026.2 · Elo: Cuidando de Quem Cuida**
