# Brincando com JWT

Aplicativo desenvolvido em Java para implementar [JWT](https://jwt.io/) com o framework [Spring Boot](http://spring.io/projects/spring-boot).

## Dependências

É necessário ter instalado na máquina o Gerenciador de dependências [Maven](https://maven.apache.org/),
caso ele não esteja instalado, siga o tutorial do próprio site: [Maven install](https://maven.apache.org/install.html).

## Instalação

### Criação do banco de dados

Antes de executar o projeto é importante realizar a criação da base de dados do repositório
e a configuração para conexão com essa base de dados.

No script SQL à seguir, troque o texto `{DATABASE}` para o nome do banco que você quer criar,
após isso, execute o script à seguir no seu banco de dados MySQL ou MariaDB:

```sql
DROP SCHEMA IF EXISTS {DATABASE};
CREATE SCHEMA IF NOT EXISTS {DATABASE} DEFAULT CHARACTER SET utf8mb4;
USE {DATABASE};

DROP TABLE IF EXISTS usuarios_tbl;
CREATE TABLE IF NOT EXISTS usuarios_tbl (
	id_usuarios    int          NOT NULL AUTO_INCREMENT,
	nome           varchar(50)  NOT NULL,
	senha          varchar(100) NOT NULL,
	situacao       tinyint(1)   NOT NULL,
	data_criacao   timestamp    NOT NULL DEFAULT current_timestamp(),
	data_alteracao timestamp    NULL     DEFAULT NULL,
	data_exclusao  timestamp    NULL     DEFAULT NULL,
	PRIMARY KEY (id_usuarios)
)
	ENGINE = InnoDB;

DROP TABLE IF EXISTS regras_tbl;
CREATE TABLE IF NOT EXISTS regras_tbl (
	id_regras      INT          NOT NULL AUTO_INCREMENT,
	nome           varchar(100) NOT NULL,
	data_criacao   timestamp    NOT NULL DEFAULT current_timestamp(),
	data_alteracao timestamp    NULL     DEFAULT NULL,
	data_exclusao  timestamp    NULL     DEFAULT NULL,
	PRIMARY KEY (id_regras)
)
	ENGINE = InnoDB;

DROP TABLE IF EXISTS regras_usuarios_tbl;
CREATE TABLE IF NOT EXISTS regras_usuarios_tbl (
	id_regras   int        NOT NULL,
	id_usuarios int        NOT NULL,
	situacao    tinyint(1) NOT NULL,
	PRIMARY KEY (id_regras,id_usuarios),
	CONSTRAINT fk_idregras_usuarios
		FOREIGN KEY (id_regras)
			REFERENCES regras_tbl(id_regras)
			ON DELETE NO ACTION
			ON UPDATE NO ACTION,
	CONSTRAINT fk_idusuarios_regras
		FOREIGN KEY (id_usuarios)
			REFERENCES usuarios_tbl(id_usuarios)
			ON DELETE NO ACTION ON UPDATE NO ACTION
)
	ENGINE = InnoDB;

DROP TABLE IF EXISTS posts_tbl;
CREATE TABLE IF NOT EXISTS posts_tbl (
	id_posts       int          NOT NULL AUTO_INCREMENT,
	titulo         varchar(240) NOT NULL,
	conteudo       longtext     NOT NULL,
	data_criacao   timestamp    NOT NULL DEFAULT current_timestamp(),
	data_alteracao timestamp    NULL     DEFAULT NULL,
	data_exclusao  timestamp    NULL     DEFAULT NULL,
	PRIMARY KEY (id_posts)
)
	ENGINE = InnoDB;

DROP TABLE IF EXISTS itens_tbl;
CREATE TABLE IF NOT EXISTS itens_tbl (
	id_itens   int          NOT NULL AUTO_INCREMENT,
	nome       varchar(60)  NOT NULL,
	descricao  varchar(100) NULL     DEFAULT NULL,
	quantidade int          NOT NULL DEFAULT 0,
	preco      decimal      NOT NULL DEFAULT 0,
	data_criacao   timestamp    NOT NULL DEFAULT current_timestamp(),
	data_alteracao timestamp    NULL     DEFAULT NULL,
	data_exclusao  timestamp    NULL     DEFAULT NULL,
	PRIMARY KEY (id_itens)
)
	ENGINE = InnoDB;
```

### A cópia do repositório

Faça o clone do repositório através do comando

```bash
git clone https://github.com/lin-br/spring-boot-brincando-jwt.git
```

Com a sua IDE preferida, abra o projeto e então realize o download das dependências
do projeto através do Maven com o goal [mvn install](https://maven.apache.org/plugins/maven-install-plugin/usage.html).

Algumas IDE do mercado já possuem integração com o Maven e com isso, a própria IDE
se encarrega de realizar a instalação das dependências.

> Caso a sua IDE não possua integração com o Maven e você não saiba como executa-lo,
recomendo fortemente que estude ou procure saber sobre o assunto.

### Configuração da conexão com a base de dados

Como padrão, o projeto está procurando o arquivo com as informações de conexão com o banco de dados 
dentro do diretório `properties/`. Abra o arquivo `localhost.properties` e altere os campos `{DATABASE}`,
`{USER}` e `{PASSWORD}`com o nome do banco de dados que você criou, o usuário e senha de acesso ao
servidor MySQL ou MariaDB, respectivamente.

## Iniciando API

Na sua IDE, execute a aplicação do projeto, geralmente é um botão chamado `RUN`, com isso o Spring Boot
começara a ser executado na porta `8080` da sua máquina.

Os endpoints do projeto são:
- [http://localhost:8080/api/login/](http://localhost:8080/api/login/)
    - Todos os métodos para o link descrito à cima.
- [http://localhost:8080/api/itens/](http://localhost:8080/api/itens/)
    - Com os métodos GET(para consultar todos itens) e POST(para cadastrar) para o link descrito à cima.
- [http://localhost:8080/api/posts/](http://localhost:8080/api/posts/)
    - Com o método POST(para cadastrar posts) para o link descrito à cima.
- [http://localhost:8080/api/usuarios/](http://localhost:8080/api/usuarios/)
    - Com o método POST(para cadastrar usuário) para o link descrito à cima.

## Consumindo API

> É possível enviar requisições através de um aplicativo como o [POSTMAN](https://www.getpostman.com/downloads/) que
você instala na máquina ou uma extensão no navegador e realiza requisições para uma URL.
> Uma outra opção é enviar comandos através do famoso **cURL**, neste caso, é o modo que eu vou adotar aqui, para
apresentar como usar o repositório. Porém para testes e uso em casos reais, eu utilizo e recomendo fortemente o POSTMAN.

### Método POST - Cadastrar usuário

URL: **http://localhost:8080/api/usuarios/**

Vai cadastrar um usuário com o nome `Lin` e a senha `123456789`.

```bash
curl -X POST \
  'http://localhost:8080/api/usuarios/' \
  -H 'Content-Type: application/json' \
  -d '{
    "nome": "Lin",
    "senha": "123456789"
}'
```

### LOGIN

URL: **http://localhost:8080/api/login/**

Realizar login na aplicação com o usuário `Lin` e a senha `123456789`.

```bash
curl -X GET \
  http://localhost:8080/api/login/ \
  -H 'Content-Type: application/json' \
  -d '{
    "nome": "lin",
    "senha": "123456789"
}'
```

Com o login realizado com sucesso, o aplicativo retorna um HEADER chamado `Authorization` com o conteúdo do
token, como por exemplo: `Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE1NTIzMjg0MjYsImV4cCI6MTU1MjMyODQ4Niwic3ViIjoiV2VzbGV5IiwicGVybWlzc29lcyI6WyJBRE1JTiJdfQ.o3eVEjDN75luzvntA4AP10pctyAeKyMwMnLLc1fjnU7euLeI5jh3lfBN9zsKXppVEdbj5iJ8CsU4d1nj1cB5iw`.
Qualquer solicitação que seja realizada no aplicativo, é necessário apresentar o JWT, como por exemplo:

```bash
curl -X GET \
  http://localhost:8080/api/itens/ \
  -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE1NTIzMjg0MjYsImV4cCI6MTU1MjMyODQ4Niwic3ViIjoiV2VzbGV5IiwicGVybWlzc29lcyI6WyJBRE1JTiJdfQ.o3eVEjDN75luzvntA4AP10pctyAeKyMwMnLLc1fjnU7euLeI5jh3lfBN9zsKXppVEdbj5iJ8CsU4d1nj1cB5iw' \
  -H 'Content-Type: application/json'
```
> Dessa maneira o Spring vai autenticar a requisição e processa-la. A expiração do token está fixa 
em 60 segundos.

### Contribuição

Caso você possua uma maneira melhor de realizar uma ação ou tarefa específica do repositório, ou até mesmo
uma dica, por mais boba que seja, enfim, clone o repositório e faça um `New pull request`. Compartilhe o seu conhecimento! 

## License
[MIT](https://choosealicense.com/licenses/mit/)