# Back-End-3-Período---Nassau-Equipe-9
Cidade Sustentável 🌱 API back-end para registro e monitoramento de problemas urbanos e ambientais. Permite envio de relatos com localização e imagens, além de oferecer um painel para gestão e acompanhamento pela administração pública, incentivando a participação da comunidade.


## Autenticação e Segurança com JWT

A API do projeto Eco Power utiliza autenticação com JWT para proteger rotas privadas. Dessa forma, apenas usuários cadastrados e autenticados conseguem acessar determinadas funcionalidades do sistema.

---

## Como cadastrar usuário

Para cadastrar um novo usuário, utilize a rota pública:

```http
POST http://localhost:8081/auth/register
```

No Postman, selecione:

```text
Body > raw > JSON
```

Exemplo de JSON para cadastro:

```json
{
  "nome": "Davi",
  "email": "davi@email.com",
  "senha": "123456"
}
```

Se o cadastro for realizado com sucesso, a API retornará os dados do usuário cadastrado. A senha será salva no banco de dados de forma criptografada utilizando BCrypt.

Exemplo de resposta:

```json
{
  "id": 1,
  "nome": "Davi",
  "email": "davi@email.com",
  "senha": "$2a$10$...",
  "role": "USER"
}
```

Caso o e-mail já esteja cadastrado, a API retornará uma mensagem informando que o e-mail já existe.

---

## Como fazer login

Para realizar login, utilize a rota pública:

```http
POST http://localhost:8081/auth/login
```

No Postman, selecione:

```text
Body > raw > JSON
```

Exemplo de JSON para login:

```json
{
  "email": "davi@email.com",
  "senha": "123456"
}
```

Se o login estiver correto, a API retornará um token JWT.

Exemplo de resposta:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

Esse token será usado para acessar as rotas privadas da API.

---

## Como usar o token JWT

Após realizar o login, copie o token retornado pela API.

No Postman, para acessar uma rota protegida:

1. Acesse a aba **Authorization**;
2. Em **Auth Type**, selecione **Bearer Token**;
3. Cole o token no campo **Token**;
4. Clique em **Send**.

O Postman enviará automaticamente o token no cabeçalho da requisição:

```http
Authorization: Bearer TOKEN_AQUI
```

Sem esse token, as rotas privadas não poderão ser acessadas.

---

## Rotas públicas

As rotas públicas podem ser acessadas sem autenticação.

| Método | Rota             | Descrição                           |
| ------ | ---------------- | ----------------------------------- |
| GET    | `/status`        | Verifica se a API está funcionando  |
| POST   | `/auth/register` | Cadastra um novo usuário            |
| POST   | `/auth/login`    | Realiza login e retorna o token JWT |

---

## Rotas privadas

As rotas privadas precisam do token JWT no cabeçalho da requisição.

| Método | Rota                | Descrição                              |
| ------ | ------------------- | -------------------------------------- |
| GET    | `/ocorrencias`      | Lista todas as ocorrências cadastradas |
| GET    | `/ocorrencias/{id}` | Busca uma ocorrência pelo ID           |
| POST   | `/ocorrencias`      | Cadastra uma nova ocorrência           |
| PUT    | `/ocorrencias/{id}` | Atualiza uma ocorrência existente      |
| DELETE | `/ocorrencias/{id}` | Remove uma ocorrência                  |
| GET    | `/usuarios`         | Lista os usuários cadastrados          |

---

## Exemplo de uso de rota protegida

Para cadastrar uma ocorrência, primeiro é necessário fazer login e copiar o token JWT.

Depois, no Postman:

```http
POST http://localhost:8081/ocorrencias
```

Na aba **Authorization**, selecione **Bearer Token** e cole o token.

No corpo da requisição:

```json
{
  "titulo": "Esgoto a céu aberto",
  "descricao": "Esgoto vazando próximo à calçada",
  "endereco": "Rua das Flores",
  "bairro": "Jardim Paulista",
  "categoria": "Esgoto",
  "prioridade": "Alta",
  "status": "Aberto"
}
```

Se o token for válido, a ocorrência será cadastrada com sucesso.

---

## Teste de segurança

Para confirmar que a autenticação está funcionando:

1. Tente acessar uma rota privada sem token;
2. A API deverá retornar erro de acesso;
3. Faça login em `/auth/login`;
4. Copie o token retornado;
5. Envie o token como Bearer Token;
6. A rota privada deverá funcionar normalmente.

Isso comprova que a API está protegida com autenticação JWT.
