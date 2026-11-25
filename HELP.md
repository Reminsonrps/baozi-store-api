# Baozi Store API

API REST de clientes, produtos e pedidos usando Java 17, Spring Boot, Spring Data JPA, H2 e Maven.

Aluno: Reminson | RU: 5287384 | Porto Seguro - Bahia

## Executar

No Windows, na raiz do projeto:

```powershell
.\mvnw.cmd spring-boot:run
```

A API fica disponível em `http://localhost:8080`. O banco H2 em memória é criado automaticamente. O console H2 fica em `http://localhost:8080/h2-console`, com JDBC URL `jdbc:h2:mem:baozistore`, usuário `sa` e senha em branco.

## Endpoints

| Método | URL | Ação |
|---|---|---|
| POST, GET | `/clientes` | Criar e listar clientes |
| GET, PUT, DELETE | `/clientes/{id}` | Consultar, atualizar e apagar cliente |
| POST, GET | `/produtos` | Criar e listar produtos |
| GET, PUT, DELETE | `/produtos/{id}` | Consultar, atualizar e apagar produto |
| POST, GET | `/pedidos` | Criar e listar pedidos |
| GET, DELETE | `/pedidos/{id}` | Consultar e apagar pedido |

Para criar pedido, envie os IDs de cliente e produto já cadastrados:

```json
{
  "cliente": { "id": 1 },
  "produto": { "id": 1 },
  "quantidade": 5
}
```

A coleção de requisições para importar no Postman está em `BaoziStore.postman_collection.json`.

## Entrega do trabalho

A API e a coleção cobrem as operações solicitadas para clientes, produtos e pedidos, incluindo o cenário do cliente Reminson 5287384, do produto Pãozinho chinês Baozi e do pedido de 5 unidades. Para completar a entrega descrita no PDF, ainda é necessário executar as requisições no Postman e anexar capturas de tela dos POSTs, GETs gerais, GETs por ID e de pelo menos um DELETE, além de publicar o projeto e substituir o link do repositório que consta como pendente no PDF.

# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.reminson.baozi-store-api' is invalid and this project uses 'com.reminson.baozi_store_api' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/4.2.0/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/4.2.0/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/4.2.0/reference/web/servlet.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/4.2.0/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Validation](https://docs.spring.io/spring-boot/4.2.0/reference/io/validation.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.
