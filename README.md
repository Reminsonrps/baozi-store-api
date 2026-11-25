# Baozi Store API

API REST para gerenciar clientes, produtos e pedidos da Baozi Store. O projeto usa Java 17, Spring Boot, Spring Data JPA e banco H2 em memória.

## Requisitos

- JDK 17 ou superior
- Windows PowerShell (ou shell compatível com o Maven Wrapper)

## Iniciar a API

Na raiz do projeto, execute:

```powershell
.\mvnw.cmd spring-boot:run
```

A API ficará disponível em `http://localhost:8080`. Para parar, pressione `Ctrl+C` no terminal.

## Usar com Postman

Importe `BaoziStore.postman_collection.json` no Postman. A coleção usa `http://localhost:8080` como `baseUrl`. Execute os pedidos nesta ordem:

1. **Criar cliente** — `POST /clientes`
2. **Criar produto** — `POST /produtos`
3. **Criar pedido** — `POST /pedidos`, usando os IDs de cliente e produto retornados

Os pedidos POST respondem com `201 Created` e incluem o ID criado. GET responde com `200 OK`, DELETE responde com `204 No Content` e consultas ou exclusões de IDs inexistentes respondem com `404 Not Found`.

## Endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/clientes` | Cadastrar cliente |
| `GET` | `/clientes` | Listar clientes |
| `GET` | `/clientes/{id}` | Consultar cliente por ID |
| `PUT` | `/clientes/{id}` | Atualizar cliente |
| `DELETE` | `/clientes/{id}` | Apagar cliente |
| `POST` | `/produtos` | Cadastrar produto |
| `GET` | `/produtos` | Listar produtos |
| `GET` | `/produtos/{id}` | Consultar produto por ID |
| `PUT` | `/produtos/{id}` | Atualizar produto |
| `DELETE` | `/produtos/{id}` | Apagar produto |
| `POST` | `/pedidos` | Cadastrar pedido |
| `GET` | `/pedidos` | Listar pedidos |
| `GET` | `/pedidos/{id}` | Consultar pedido por ID |
| `DELETE` | `/pedidos/{id}` | Apagar pedido |

### Exemplos de requisição

Criar um cliente:

```http
POST /clientes
Content-Type: application/json
```

```json
{
  "nome": "Reminson 5287384",
  "clienteDesde": "2026-11-24"
}
```

Criar um produto:

```http
POST /produtos
Content-Type: application/json
```

```json
{
  "nome": "Pãozinho chinês Baozi",
  "preco": 5.00,
  "estoque": true
}
```

Criar um pedido (substitua `1` pelos IDs retornados nos cadastros):

```http
POST /pedidos
Content-Type: application/json
```

```json
{
  "cliente": { "id": 1 },
  "produto": { "id": 1 },
  "quantidade": 5
}
```

## Banco H2

O banco é criado em memória e suas tabelas são gerenciadas pelo JPA. Enquanto a aplicação estiver rodando, acesse o console em `http://localhost:8080/h2-console` com:

- JDBC URL: `jdbc:h2:mem:baozistore`
- Usuário: `sa`
- Senha: em branco

Os dados são apagados ao encerrar ou reiniciar a aplicação. Cadastre novamente cliente e produto antes de criar pedidos em uma nova execução.

## Testes

Execute os testes automatizados com:

```powershell
.\mvnw.cmd test
```
