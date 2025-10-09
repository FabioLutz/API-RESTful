# API RESTful

Uma API RESTful com CRUD desenvolvida em Java com Spring Boot e persistência em PostgreSQL, rodando via Docker Compose.

## Tecnologias usadas

- **Linguagem**: Java 21
- **Framework**: Spring Boot 3
- **Segurança**: Spring Security + JWT (Auth0/java-jwt)
- **Persistência**: Spring Data JPA + PostgreSQL
- **Migrations**: Liquibase
- **Mapeamento**: MapStruct + Lombok
- **Validação**: Bean Validation (Jakarta)
- **Containerização**: Docker (multi-stage build) + Docker Compose
- **Testes**: JUnit 5, Spring Boot Test, Mockito

## Como rodar

### Pré-requisitos

- Docker
- Docker Compose

### Como rodar

1. Clone o repositório:
    ```shell
    git clone https://github.com/FabioLutz/API-RESTful.git
    cd API-RESTful
    ```

2. Crie uma cópia do `.env.example` nomeando de `.env`

3. Inicie os serviços:
   ```shell
   docker compose up --build
   ```

4. Aguarde até que todos os serviços estejam disponíveis

5. A API estará rodando em http://localhost:8080
