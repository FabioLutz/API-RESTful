# API RESTful

Este projeto tem como objetivo estudar como funciona a criação de uma API RESTful utilizando Spring Boot com Java

## Como rodar

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

### Rodar a aplicação

1. Inicie com `docker compose up --build`
2. Finalize com `docker compose down`
