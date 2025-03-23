# API Documentation

## Table of Contents

- [API Endpoints](#api-endpoints)
- [DTO Structures](#dto-structures)

---

# API Endpoints

- `/api`
  - `/api/session` **GET** -> SessionDTO = [GET CURRENT SESSION]
  - `/api/catalog` **GET** (Optional Query params: `query`, `minPrice`, `maxPrice`) -> List of CatalogItemDTO
  - `/api/gestion`
    - `/api/gestion/sessions` **GET** -> List of SessionDTO
    - `/api/gestion/games` **GET** (Optional Query param: `query`) -> List of IdGameDTO
    - `/api/gestion/transactions` **GET** -> List of TransactionDTO
    - `/api/gestion/deposits` **POST** (List of DepositItemDTO) = [CREATE DEPOSIT]
    - `/api/gestion/clients` **POST** (ClientDTO), **PUT** (Query param: `id`, ClientDTO), **GET** (Optional Query params: `id`, `email`) -> List of IdClientDTO = [CREATE, UPDATE, GET]
      - `/api/gestion/clients/realgames` **GET** (Query param: `id`) -> List of IdRealgameDTO
      - `/api/gestion/clients/due` **GET** (Query param: `id`) -> string
      - `/api/gestion/clients/withdrawn` **GET** (Query param: `id`) -> string
      - `/api/gestion/clients/withdraw` **GET** (Query param: `id`) = [PAY THE CLIENT]
    - `/api/gestion/sales` **POST** (List of Realgame id) = [SELL REALGAMES]
      - `/api/gestion/sales/realgames` **GET** (**Optional** Query param: `query`) -> List of IdRealgameDTO
    - `/api/gestion/totals`
      - `/api/gestion/totals/fee` **GET** -> string
      - `/api/gestion/totals/due` **GET** -> string
      - `/api/gestion/totals/commission` **GET** -> string
      - `/api/gestion/totals/paid` **GET** -> string
      - `/api/gestion/totals/treasury` **GET** -> string
  - `/api/admin`
    - `/api/admin/clients` **DELETE** (Query param: `id`)
    - `/api/admin/sessions` **POST** (SessionDTO), **PUT** (Query param: `id`, SessionDTO), **DELETE** (Query param: `id`) = [CREATE, UPDATE, DELETE]
    - `/api/admin/games` **POST** (GameDTO), **PUT** (Query param: `id`, GameDTO), **DELETE** (Query param: `id`) = [CREATE, UPDATE, DELETE]
  - `/api/auth`
    - `/api/auth/login` **POST** (UserDTO) -> token
    - `/api/auth/register` **POST** (UserDTO) -> token

---

# DTO Structures

## CatalogItemDTO
```json
{
  "unitPrice": "number",
  "quantity": "integer (int32)",
  "gameName": "string",
  "gameEditor": "string",
  "sellerName": "string",
  "sellerSurname": "string"
}
```

## ClientDTO

```json
{
  "name": "string",
  "surname": "string",
  "email": "string",
  "phoneNumber": "string",
  "address": "string"
}
```

## DepositItemDTO
```json
{
  "unitPrice": "number",
  "quantity": "integer (int32)",
  "game": "GameDTO",
  "client": "ClientDTO"
}
```

## GameDTO
```json
{
  "name": "string",
  "editor": "string"
}
```

## IdClientDTO
```json
{
  "id": "integer (int64)",
  "name": "string",
  "surname": "string",
  "email": "string",
  "phoneNumber": "string",
  "address": "string"
}
```

## IdGameDTO
```json
{
  "id": "integer (int64)",
  "name": "string",
  "editor": "string"
}
```

## IdRealgameDTO
```json
{
  "id": "integer (int64)",
  "unitPrice": "number",
  "status": "Status",
  "name": "string",
  "editor": "string"
}
```

## RealgameDTO
```json
{
  "unitPrice": "number",
  "status": "Status",
  "name": "string",
  "editor": "string"
}
```

## SessionDTO
```json
{
  "beginDate": "date",
  "endDate": "date",
  "commission": "number",
  "fees": "number"
}
```

## TransactionDTO
```json
{
  "date": "date",
  "value": "number",
  "type": "Type{DEPOSIT, COMMISSION, SALE, PAY}"
}
```

## LoginUserDTO
```json
{
  "email": "bite",
  "password": "string"
}
```

## RegisterUserDTO
```json
{
  "email": "string",
  "password": "string",
  "role": "Role{GESTION, ADMIN}"
}
```