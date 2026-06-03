# NutriSpace

Sistema de monitoramento de **estufas biológicas espaciais** — Global Solution (FIAP · Java Advanced).

API REST em **Java 17** e **Spring Boot 3.4**, com persistência em **Oracle**, autenticação **JWT**, documentação **Swagger** e arquitetura em camadas.

---

## Links do projeto

| Item | Link |
|------|------|
| Repositório GitHub | `https://github.com/SEU_USUARIO/nutrispace` |
| Deploy (API pública) | `https://SUA_URL_DEPLOY` |
| Swagger / OpenAPI | `https://SUA_URL_DEPLOY/swagger-ui.html` |
| Vídeo apresentação (até 10 min) | `https://youtu.be/SEU_VIDEO` |
| Pitch GS (até 3 min) | `https://youtu.be/SEU_PITCH` |

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
├── model        → Entidades, enums, embeddables, herança JPA
├── dto          → Request DTOs + Java Records de response
├── config       → Security, JWT, CORS, Swagger
└── exception    → Exceções de domínio
```

### Modelagem avançada JPA

| Recurso | Implementação |
|---------|----------------|
| `@Embedded` | `CondicoesIdeais`, `MedicaoAmbiental`, `CapacidadeReservatorio` |
| `@MappedSuperclass` | `RegistroVinculadoEstufa`, `PessoaBase` |
| Herança | `PessoaBase` → `Astronauta`; `RegistroVinculadoEstufa` → Alerta, Leitura, Colheita, Rega |
| Agrupamento composto | `@Embeddable` agrupa campos relacionados (ex.: condições ideais da planta) |

---

## Pré-requisitos

- JDK 17+
- Maven 3.9+
- Oracle com schema e tabelas `TB_NS_*` (DDL da disciplina de database)
- Sequences: `src/main/resources/db/sequences-oracle.sql`

---

## Configuração e execução

### Oracle (produção / entrega)

1. Execute o DDL das tabelas `TB_NS_*` e, se necessário, `sequences-oracle.sql`.
2. Credenciais em `src/main/resources/application-oracle.properties`:

```properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:ORCL
spring.datasource.username=rm563960
spring.datasource.password=020607
spring.jpa.properties.hibernate.default_schema=NUTRISPACE
```

3. Inicie com o perfil `oracle`:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=oracle
```

### Desenvolvimento (H2 em memória)

O perfil padrão é `dev` (H2). Para executar:

```bash
./mvnw spring-boot:run
```

API: `http://localhost:8080`  
Swagger: `http://localhost:8080/swagger-ui.html`  
Raiz HATEOAS: `http://localhost:8080/`

Usuário seed (perfil `dev`): `admin@nutrispace.com` / `123456`

---

## Autenticação JWT

1. Cadastre um astronauta: `POST /astronautas` (senha armazenada com BCrypt).
2. Login: `POST /auth/login`

```json
{
  "email": "admin@nutrispace.com",
  "senha": "123456"
}
```

3. Envie o token nas demais rotas:

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
- `NutriSpace-Local.postman_environment.json`

---

## Deploy

```bash
docker build -t nutrispace-api .
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:oracle:thin:@host:1521:ORCL \
  -e SPRING_DATASOURCE_USERNAME=user \
  -e SPRING_DATASOURCE_PASSWORD=pass \
  -e SPRING_PROFILES_ACTIVE=oracle \
  nutrispace-api
```