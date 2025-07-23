# API RESTful

Este projeto tem como objetivo estudar como funciona a criação de uma API RESTful utilizando Spring Boot com Java

## Como rodar

Aqui mostra como rodar o sistema no **Linux**

Caso utilize outro, talvez seja necessário adaptar algumas coisas

### Configurações do ambiente

Defina nas variáveis de ambiente os valores do contexto de seu projeto

Há duas opções:

- Definir nas variáveis de ambiente do sistema
- Criar arquivo `.env` e carregá-lo quando for usar

Variáveis de ambiente necessárias:

| Variável | Descrição | Exemplo |
|:---:| --- | --- |
| DB_HOST | Host do banco de dados | localhost |
| DB_PORT | Porta do banco de dados | 5432 |
| DB_DATABASE | Nome do banco de dados | db |
| DB_USER | Usuário do banco de dados | user |
| DB_PASSWORD | Senha do banco de dados | s3nH4%F0rT3 |
| SECRET | Segredo para o JWT | segredoseguroeforte |

### Rodar banco de dados

A API foi configurada para usar dois tipos de banco de dados:

- **H2**: Em memória e mais rápido para testes rápidos
- **PostgreSQL via Docker**: Não necessita do PostgreSQL instalado na máquina e é usado para testes mais preciso

#### H2

Há duas opções:

- **Definir variável**: `SPRING.PROFILES.ACTIVE=dev`
- **Adicionar ao `.env`**: `export SPRING_PROFILES_ACTIVE=dev`

#### PostgreSQL via Docker

1. Inicie com `docker compose up -d`
2. Finalize com `docker compose down`

### Rodar API

1. Instale a OpenJDK 21 e Maven
2. Instale dependências com `mvn dependency:resolve`
3. Inicie com `mvn spring-boot:run`
4. Finalize dentro dele com `CTRL+C`
