# ReadME

## Queimadas UFSCar Backend

Repositório contendo código fonte do app backend do Brigada Online, com licensa MIT. Tecnologias usadas:

| Tecnologia | Versão |
| ------ | ------ |
| Java | 15 |
| SpringBoot 2 | 2.4.3 |
| Gradle | 7.0.0 |
| PostgreSQL | 11.0 |

Para inicializar a aplicação, é necessário setar variáveis de ambiente:

```shell
DATABASE_URL = url de conexão do banco
PG_USER = usuário de conexão do banco
PG_PASS = senha do banco
SERVER_PORT = porta da aplicação
```

## Subindo a aplicação

Basta rodar o comando
`./gradlew bootRun`