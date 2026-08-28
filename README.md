# Documentação do Projeto — Kadu Bank

> ⚠️ **Status:** projeto em desenvolvimento (ainda não finalizado). Esta documentação reflete o que já foi implementado até o momento e será atualizada conforme o projeto evolui.

## Visão Geral

Este é um projeto de API desenvolvido em **Java** com **Spring Boot**, com uma tela de acesso em **HTML/CSS** consumindo essa API. Segue a arquitetura em camadas padrão do Spring (Controller → Service → dados).

O projeto conta atualmente com dois módulos principais:

- **User** — autenticação (login) e cadastro de usuários. **Já implementado e funcional.**
- **Conta** — operações relacionadas a contas bancárias. **Ainda não implementado** (só planejado).

---

## Arquitetura

```
Front-end (HTML/CSS/JS)  →  Controller  →  Service  →  dados em memória (Map)
```

```
src/
└── main/
    └── java/
        └── banck/client/kadu/
            ├── Controller/
            │   └── UserController.java
            └── services/
                └── UserService.java

resources (front-end)
├── userLogin.html
└── user.css
```

> Observação: os dados de usuário hoje são guardados em memória (`HashMap`), não em um banco de dados real. Isso significa que a lista de usuários **zera toda vez que a aplicação reinicia**. É um estágio inicial de testes — trocar por um banco de dados persistente (JPA + banco relacional, por exemplo) é um passo natural mais à frente.

---

## Módulo: User (implementado)

### `UserController`

```java
@RestController
@RequestMapping("/api/user")
```

Recebe as requisições HTTP e repassa para o `UserService`, injetado via `@Autowired`.

**Endpoints:**

| Método | Rota | Parâmetros (form-urlencoded) | Retorno em sucesso | Retorno em falha |
|---|---|---|---|---|
| `POST` | `/api/user/login` | `userName`, `userPassword` | `200 OK` — "login realized with success!!" | `401 UNAUTHORIZED` — "Name or Passwords invalid" |
| `POST` | `/api/user/register` | `userName`, `userPassword` | `200 OK` — "User registerd with success!!" | `409 CONFLICT` — "Name already register" |

Repare que os parâmetros são recebidos com `@RequestParam`, ou seja, **não é um JSON no corpo** — o front-end manda os dados como `application/x-www-form-urlencoded` (isso é o que o `userLogin.html` já faz, usando `URLSearchParams`).

### `UserService`

```java
@Service
public class UserService {
    private Map<String, String> usuarios = new HashMap<>();
    ...
}
```

- Usa um `HashMap<String, String>` como "banco" temporário, onde a chave é o `userName` e o valor é a `userPassword`.
- No construtor, já cria um usuário de teste (`joao` / `123456`) para facilitar os testes manuais.
- `registerUser(userName, userPassword)`: verifica se o nome já existe (`containsKey`); se já existir, retorna `false` (cadastro recusado); senão, cadastra e retorna `true`.
- `login(userName, userPassword)`: retorna `true` somente se o nome existir no mapa **e** a senha bater com a guardada.

---

## Front-end: Tela de Acesso (`userLogin.html` + `user.css`)

Tela única com duas abas alternáveis via JavaScript: **Entrar** (login) e **Abrir conta** (cadastro). Visual escuro com destaque laranja, seguindo uma identidade "Kadu Bank".

### Fluxo de Login

1. Usuário digita `userName` e `userPassword` nos inputs.
2. Valida no próprio front que os campos não estão vazios.
3. Chama `chamarApi('/login', { userName, userPassword })`, que monta um `POST` para `/api/user/login` com o corpo em `application/x-www-form-urlencoded`.
4. Se a resposta for OK (200), mostra mensagem de sucesso e redireciona para `index.html`.
5. Se for `401`, mostra a mensagem de erro vinda do back-end (ou uma padrão).

## Banco de dados
<h1>Create bank of data</h1>
<p>O banco de dados foi criado agora com todas as tabelas e integraçao com uma maquina interna
do computador, nao esta funcionando normalmente com deploy por ser um banco de maquina pessoal.</p>
<h1>Atualizaçoes necessarias!!</h1>
<p>Se algum usuario ou se o propio dono do codigo quiser, pode trocar o banco de dados (MySQL) por PostGres.</p>

### Fluxo de Cadastro

1. Usuário digita `userName`, `userPassword` e confirma a senha.
2. Validações no front: campos preenchidos, senha com no mínimo 6 caracteres, senha e confirmação iguais.
3. Chama `chamarApi('/register', { userName, userPassword })` → `POST /api/user/register`.
4. Se OK, mostra sucesso, limpa o formulário e volta para a aba de login automaticamente.
5. Se `409` (nome já em uso) ou outro erro, mostra a mensagem correspondente.

### Estilo (`user.css`)

- Paleta escura (`--fundo`, `--superficie`) com destaque em laranja (`--laranja`).
- Cartão central (`.cartao`) com abas deslizantes (`.indicador-aba`) alternando entre os formulários de login e registro.
- Responsivo para telas menores (`@media (max-width: 440px)`).

---

## Módulo: Conta (ainda não implementado)

Planejado, mas ainda não presente no código:

- `ContaController` — vai concentrar as operações de conta (ex.: depósito, saque, consulta de saldo).
- `ContaService` — lógica de negócio da conta.
- Uma classe privada `conta`, para criar uma **conta zerada de teste**, no mesmo espírito do usuário `joao` de teste no `UserService`.

---

## Tecnologias Utilizadas

- **Java** + **Spring Boot** (back-end REST)
- **HTML5 / CSS3 / JavaScript** (front-end da tela de acesso)
- Dados em memória (`HashMap`) — sem banco de dados persistente por enquanto