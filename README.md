# Kadu Bank

> Aplicação web de simulação bancária desenvolvida com Java e Spring Boot, com foco em autenticação, persistência de dados e operações básicas de conta.

**Status:** Em desenvolvimento — projeto educacional e de portfólio. Não utilizar para movimentações financeiras reais.

## Visão geral

O **Kadu Bank** é uma aplicação que demonstra a construção de um sistema bancário simplificado. Cada usuário cadastrado possui uma conta associada, permitindo consultar o saldo e realizar operações de depósito e saque dentro da aplicação.

O projeto evoluiu de uma implementação inicial com armazenamento em memória para uma arquitetura baseada em banco de dados relacional, com separação entre controllers, services e repositories.

### Funcionalidades

- Cadastro de usuários com validação de e-mail e senha.
- Armazenamento de senhas com hash BCrypt.
- Login e logout utilizando sessão HTTP.
- Criação de uma conta vinculada ao usuário no cadastro.
- Consulta dos dados da conta autenticada e do saldo.
- Operações de depósito e saque.
- Persistência de usuários e contas em PostgreSQL.
- Interface web com HTML, CSS e JavaScript.

> **Nota:** os recursos acima representam as funcionalidades previstas e presentes na implementação. A versão atual ainda exige correções de integração antes de ser considerada pronta para execução. Consulte [Estado de desenvolvimento](#estado-de-desenvolvimento).

## Tecnologias

| Tecnologia | Utilização |
|---|---|
| Java 17 | Linguagem principal |
| Spring Boot 3.3.4 | Estrutura do back-end |
| Spring Web | Endpoints REST |
| Spring Data JPA | Persistência e acesso aos dados |
| Hibernate | Mapeamento objeto-relacional |
| PostgreSQL | Banco de dados relacional |
| Supabase | Hospedagem do PostgreSQL |
| BCrypt | Hash de senhas |
| Jakarta Validation | Validação de dados |
| Maven | Dependências e build |
| HTML5, CSS3 e JavaScript | Interface e comunicação com a API |

## Arquitetura

A aplicação utiliza uma arquitetura em camadas para separar responsabilidades e facilitar a manutenção.

```text
Navegador
   |
   | HTTP / Fetch API
   v
Controllers
   |
   v
Services
   |
   v
Repositories
   |
   v
PostgreSQL (Supabase)
```

- **Controller:** recebe requisições HTTP, verifica dados de entrada e devolve respostas.
- **Service:** concentra regras de negócio, como cadastro e movimentação do saldo.
- **Repository:** realiza operações de persistência por meio do Spring Data JPA.
- **Entity/Model:** representa as estruturas persistidas no banco.
- **DTO/Request:** define os dados recebidos ou expostos pela API.

## Estrutura do projeto

```text
BankOfKadu/
├── src/
│   └── main/
│       ├── java/banck/client/kadu/
│       │   ├── Config/
│       │   │   └── SecurityConfig.java
│       │   ├── Controller/
│       │   │   ├── AccountController.java
│       │   │   ├── CountController.java
│       │   │   └── UserController.java
│       │   ├── DTO/
│       │   │   ├── AccountResponse.java
│       │   │   └── RegisterRequest.java
│       │   ├── Repository/
│       │   │   ├── AccountRepository.java
│       │   │   ├── Accounts.java
│       │   │   ├── User.java
│       │   │   └── UserRepository.java
│       │   ├── Request/
│       │   │   └── ValorRequest.java
│       │   ├── services/
│       │   │   ├── CountService.java
│       │   │   └── UserService.java
│       │   └── KaduApplication.java
│       └── resources/
│           ├── application.yml
│           └── static/
│               ├── index.html
│               ├── user.css
│               └── userLogin.html
├── pom.xml
└── README.md
```

## Banco de dados

O projeto utiliza **PostgreSQL**, hospedado no Supabase. As credenciais são obtidas por variáveis de ambiente, evitando armazenar usuário e senha do banco diretamente no código versionado.

### Modelo de dados

A implementação possui duas entidades centrais:

**User**
- Identificador do usuário.
- E-mail.
- Senha armazenada em formato de hash.

**Accounts**
- Identificador da conta.
- Número da conta.
- Saldo.
- Referência ao usuário proprietário.

A relação entre usuário e conta é de um para um (`OneToOne`). A coluna `user_id` é obrigatória e única.

## Configuração e execução

### Pré-requisitos

- JDK 17 ou superior.
- Maven.
- Instância PostgreSQL acessível pela aplicação.
- Credenciais de conexão com o banco.

### 1. Clonar o repositório

```bash
git clone https://github.com/kaduzixs/BankOfKadu.git
cd BankOfKadu
```

### 2. Configurar as variáveis de ambiente

A aplicação espera as variáveis:

| Variável | Descrição |
|---|---|
| `DATABASE_USERNAME` | Usuário do PostgreSQL |
| `DATABASE_PASSWORD` | Senha do PostgreSQL |

Exemplo para uma sessão de terminal Linux:

```bash
export DATABASE_USERNAME="seu_usuario"
export DATABASE_PASSWORD="sua_senha"
```

Configure esses valores no ambiente em que a aplicação será executada. Não publique credenciais reais no repositório.

A URL JDBC definida em `src/main/resources/application.yml` aponta atualmente para o PostgreSQL do projeto no Supabase. Para utilizar outro banco, ajuste a URL conforme o ambiente.

### 3. Executar a aplicação

```bash
./mvnw spring-boot:run
```

Se o repositório não possuir Maven Wrapper, utilize:

```bash
mvn spring-boot:run
```

A aplicação utiliza a porta padrão **8080**, salvo configuração diferente.

> A execução depende da correção dos pontos indicados em [Estado de desenvolvimento](#estado-de-desenvolvimento).

## API

Endpoints identificados nos controllers atuais:

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/user/register` | Cadastra um usuário |
| `POST` | `/api/user/login` | Autentica o usuário |
| `POST` | `/api/user/logout` | Encerra a sessão |
| `GET` | `/api/account/me` | Retorna os dados da conta da sessão atual |
| `GET` | `/api/conta/saldo` | Consulta o saldo do usuário autenticado |
| `POST` | `/api/conta/depositar` | Realiza um depósito |
| `POST` | `/api/conta/sacar` | Realiza um saque |

### Exemplos de requisição

**Cadastro — `POST /api/user/register`**

O cadastro recebe JSON:

```json
{
  "email": "usuario@exemplo.com",
  "password": "senha-forte"
}
```

A implementação utiliza um DTO com validação de e-mail e tamanho da senha.

**Login — `POST /api/user/login`**

O login recebe `email` e `password` como parâmetros de formulário (`application/x-www-form-urlencoded`). Após a autenticação, o identificador do usuário é armazenado na sessão HTTP.

**Depósito — `POST /api/conta/depositar`**

**Saque — `POST /api/conta/sacar`**

As operações recebem JSON com o campo `valor`:

```json
{
  "valor": 100.00
}
```

Os endpoints de conta utilizam a sessão autenticada para identificar o proprietário. O cliente não deve escolher o ID de outro usuário para consultar ou movimentar uma conta.

### Respostas HTTP

- **200 OK:** operação ou consulta concluída.
- **201 Created:** cadastro realizado.
- **400 Bad Request:** dados inválidos ou operação não permitida.
- **401 Unauthorized:** sessão ausente ou credenciais inválidas.
- **404 Not Found:** conta não encontrada.
- **409 Conflict:** e-mail já cadastrado.

As respostas exatas podem variar conforme o endpoint e o tratamento de erros.

## Segurança e integridade

O projeto já contém algumas medidas importantes para um sistema de estudo:

- **Hash de senha com BCrypt:** a senha não é armazenada diretamente em texto puro.
- **Sessão HTTP:** o servidor mantém o identificador do usuário autenticado na sessão.
- **Separação por usuário:** a consulta da conta utiliza o ID associado à sessão, em vez de confiar em um ID enviado livremente pelo cliente.
- **Validação de entrada:** existem DTOs com anotações de validação para cadastro e valores monetários.
- **Credenciais externas ao código:** usuário e senha do banco são obtidos por variáveis de ambiente.
- **Precisão monetária:** saldos e valores utilizam `BigDecimal`, evitando problemas de precisão comuns em cálculos com `double`.

### Limitações de segurança

O projeto **não é um sistema bancário de produção**. Antes de qualquer uso real, seriam necessárias, entre outras medidas:

- Proteção contra CSRF e endurecimento dos cookies de sessão.
- Renovação do identificador da sessão após o login e políticas de expiração.
- HTTPS obrigatório em ambiente publicado.
- Controle de tentativas de login e proteção contra abuso.
- Tratamento centralizado e seguro de exceções.
- Garantias transacionais e controle de concorrência nas movimentações.
- Auditoria e histórico das operações.
- Testes automatizados de autenticação, autorização e regras financeiras.
- Revisão de segurança independente.

O uso de BCrypt e sessões, isoladamente, não equivale à implementação completa do Spring Security nem garante segurança de nível bancário.

## Interface

O front-end está localizado em `src/main/resources/static` e utiliza:

- **HTML5** para estrutura das páginas.
- **CSS3** para identidade visual e responsividade.
- **JavaScript** para interação e comunicação com os endpoints por meio de Fetch API.

As páginas principais são `userLogin.html` para acesso e `index.html` para o painel da conta.

## Estado de desenvolvimento

O projeto está em evolução. Esta documentação descreve a arquitetura e as funcionalidades identificadas nos arquivos do repositório, mas a versão atual ainda precisa de uma revisão de integração antes de ser considerada pronta para execução.

Pontos identificados no código que precisam ser corrigidos ou verificados:

- O record `ValorRequest` precisa ter sua declaração corrigida para compilar.
- O `CountController` mistura nomes de variáveis e acessos ao record; deve utilizar os acessores corretos e aplicar `@Valid` de forma consistente.
- O `UserController` precisa importar `RegisterRequest` e as anotações de validação utilizadas.
- As operações de depósito e saque devem validar os valores também na camada de serviço, não apenas no controller.
- É recomendável revisar o tratamento de exceções e adicionar testes automatizados.

Esses itens são apresentados para manter a documentação fiel ao estado atual do código, e não como funcionalidades já concluídas.

## Próximas melhorias

- Corrigir e validar o fluxo completo de cadastro, login e operações de conta.
- Criar testes unitários e de integração.
- Implementar histórico de transações.
- Adicionar transferências entre contas.
- Reforçar o gerenciamento de sessão e a proteção CSRF.
- Implementar controle de concorrência para operações simultâneas.
- Melhorar o tratamento padronizado de erros.
- Preparar configurações específicas para desenvolvimento e produção.
- Documentar a API com OpenAPI/Swagger.
- Automatizar build e testes com CI.

## Objetivo do projeto

O Kadu Bank é um projeto de aprendizado e portfólio voltado à prática de desenvolvimento back-end com Java e Spring Boot. Ele permite demonstrar conceitos como APIs REST, arquitetura em camadas, persistência relacional, autenticação baseada em sessão, hash de senhas, validação de dados e regras de negócio envolvendo valores monetários.

---

**Repositório:** https://github.com/kaduzixs/BankOfKadu