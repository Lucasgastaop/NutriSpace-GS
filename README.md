# NutriSpace

Sistema de monitoramento de **estufas biológicas espaciais** — Global Solution (FIAP · Java Advanced).

API REST em **Java 17** e **Spring Boot 3.4**, com persistência em **Oracle**, autenticação **JWT**, documentação **Swagger** e arquitetura em camadas.

---

## Links do projeto

| Item | Link |
|------|------|
| Repositório GitHub | https://github.com/Lucasgastaop/NutriSpace-GS |
| Deploy (API pública) | https://nutrispace-gs.onrender.com |
| Swagger / OpenAPI | https://nutrispace-gs.onrender.com/swagger-ui.html |
| Vídeo apresentação (até 10 min) | https://youtu.be/k46DfAwrLTg |
| Pitch GS (até 3 min) | https://youtu.be/-nUa6N2nbdg?si=1uGrQe2PrzVvx0sx |

---

## Equipe

| Nome | RM |
|------|-----|
| Lucas Silva Gastão Pinheiro | 563960 |
| Geovanne Coneglian Passos | 562673 |
| Guilherme Soares De Almeida | 563143 |

---

## Tecnologias

- Java 17
- Spring Boot 3.4 (Web, Data JPA, Validation, HATEOAS, Security)
- Spring Data JPA + Hibernate
- Oracle Database (`ojdbc11`)
- Lombok
- SpringDoc OpenAPI 3 (Swagger UI)
- JWT (jjwt 0.12)
- Spring Boot DevTools
- Maven
- Docker (opcional)

---

## Arquitetura

```
com.nutrispace
├── NutrispaceApplication.java
├── controller   → REST, HATEOAS, tratamento global de erros
├── service      → Regras de negócio
├── repository   → Spring Data JPA
├── model        → Entidades JPA (8 tabelas TB_NS_*)
├── dto          → Request DTOs + Java Records de response
├── config       → Security, JWT, CORS, Swagger
└── exception    → Exceções de domínio
```

### Modelagem JPA

8 entidades `@Entity` — uma para cada tabela `TB_NS_*`:

| Entidade | Tabela |
|----------|--------|
| `Planta` | `TB_NS_PLANTA` |
| `Astronauta` | `TB_NS_ASTRONAUTA` |
| `Estufa` | `TB_NS_ESTUFA` |
| `Reservatorio` | `TB_NS_RESERVATORIO` |
| `AlertaCritico` | `TB_NS_ALERTA_CRITICO` |
| `LeituraSensor` | `TB_NS_LEITURA_SENSOR` |
| `Colheita` | `TB_NS_COLHEITA` |
| `HistoricoRega` | `TB_NS_HISTORICO_REGA` |

Enums ficam dentro das entidades que os utilizam (`Estufa.StatusBomba`, `AlertaCritico.StatusAlerta`, etc.).

---

## Pré-requisitos

- JDK 17+
- Maven 3.9+
- Oracle FIAP com tabelas e dados já cadastrados (disciplina de database)

---

## Configuração e execução

### Oracle FIAP (padrão)

A API conecta ao Oracle FIAP e utiliza os **dados já existentes** no usuário `rm563960` (não é necessário rodar scripts SQL).

Credenciais de conexão em `application-oracle.properties`:

```properties
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
spring.datasource.username=rm563960
spring.datasource.password=020607
```

Inicie:

```bash
./mvnw spring-boot:run
```

API: `http://localhost:8080`  
Swagger: `http://localhost:8080/swagger-ui.html`

---

## Autenticação JWT

1. Login com astronauta cadastrado no Oracle: `POST /auth/login`

Use o e-mail e senha gravados na tabela `TB_NS_ASTRONAUTA`.

- Padrão do DDL: `lucas@nutrispace.com` / `senha123`
- Se a collection Postman já rodou antes, o banco pode estar com `admin@nutrispace.com` / `123456` — restaure com:

```sql
UPDATE tb_ns_astronauta
SET email = 'lucas@nutrispace.com', senha = 'senha123'
WHERE id_astronauta = 1;
```

2. Envie o token nas demais rotas:

```
Authorization: Bearer {token}
```

Rotas públicas: `/`, `/auth/login`, `/swagger-ui/**`, `/v3/api-docs/**`

---

## Principais endpoints

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/` | Links HATEOAS |
| POST | `/auth/login` | Login JWT |
| CRUD | `/plantas` | Plantas (HATEOAS + Records) |
| CRUD | `/estufas` | Estufas |
| CRUD | `/reservatorios` | Reservatórios |
| CRUD | `/astronautas` | Astronautas |
| GET | `/alertas/ativos` | Alertas críticos ativos |
| PATCH | `/alertas/{id}/resolver` | Resolver alerta |
| POST | `/telemetria` | Leitura IoT (+ alerta automático) |
| POST | `/colheitas` | Registrar colheita |
| POST | `/regas` | Registrar rega |

---

## Regras de negócio

- Nível do reservatório entre **0% e 100%**.
- Telemetria fora da faixa de temperatura da planta gera **AlertaCritico** com status `ATIVO`.
- Umidade da leitura entre 0 e 100.

---

## Testes

```bash
./mvnw test
```

Cobertura inclui testes unitários (`AlertaCriticoService`, `PlantaService`, `JwtService`), teste WebMvc (`PlantaController`) e contexto Spring com perfil `test` (H2).

---

## Postman

Importe os arquivos da pasta `postman/`:

- `NutriSpace.postman_collection.json`
- `NutriSpace-Render.postman_environment.json` (deploy público)
- `NutriSpace-Local.postman_environment.json` (localhost)

**Como executar:** selecione o environment → clique com o botão direito na collection → **Run collection** → **Run NutriSpace API**. As requisições estão na ordem correta; mantenha **Keep variable values** marcado.

---

## Deploy

Configure no Render:

| Variável | Valor |
|----------|-------|
| `SPRING_PROFILES_ACTIVE` | `oracle` |
| `SPRING_DATASOURCE_URL` | `jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL` |
| `SPRING_DATASOURCE_USERNAME` | `rm563960` |
| `SPRING_DATASOURCE_PASSWORD` | `020607` |
| `HIKARI_MAX_POOL_SIZE` | `1` |

**Importante:** o Oracle FIAP limita sessões simultâneas por usuário (`ORA-02391`). Antes do deploy:

- Encerre a API rodando **localmente** (mesmo usuário `rm563960`)
- Aguarde 1–2 minutos para sessões antigas expirarem
- No Render use `HIKARI_MAX_POOL_SIZE=1`; localmente o padrão é **2** (evita timeout com Postman)

A primeira subida no Render pode levar **3–4 minutos** (conexão + validação JPA no Oracle FIAP).

Localmente:

```bash
./mvnw spring-boot:run
```
