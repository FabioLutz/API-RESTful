# API RESTful

Uma API completa para cadastro, autenticação e gerenciamento de usuários, com segurança baseada em JWT e migrações de banco gerenciadas pelo Liquibase.

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

## Endpoints

Rotas como `PATCH /profile` exigem o header `Authorization: Bearer <token>`.  
O token é validado usando a biblioteca java-jwt com _secret_ do `.env`.

### Autenticação
- `POST /login`  
  Autentica usuário e retorna token JWT  
  **Corpo**:
    ```json
    {
      "email": "name@email.mail",
      "password": "Password"
    }
    ```
  **Exemplo de resposta bem-sucedida (200 OK)**:
    ```json
    {
      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.xxxxx"
    }
    ```

### Usuários
- `POST /register`  
  Registra novo usuário (verifica duplicidade de e-mail/username)  
  **Corpo**:
    ```json
    {
      "email": "name@email.mail",
      "username": "nome",
      "password": "Password"
    }
    ```

- `GET /profile/{username}`  
  Retorna dados públicos do usuário.  

- `PATCH /profile`  
  Altera senha
  **Corpo**:
    ```json
    {
      "email": "name@email.mail",
      "password": "Password",
      "newPassword": "NewPassword"
    }
    ```

- `DELETE /profile`  
  Deleta conta  
  **Corpo**:
    ```json
    {
      "email": "name@email.mail",
      "password": "Password"
    }
    ```
