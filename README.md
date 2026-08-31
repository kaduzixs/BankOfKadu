# Project Documentation — Kadu Bank

> **Status:** Project development success, but can gona have alterations for stayed better.

---

# 🏦 Kadu Bank

**Kadu Bank** is a banking system project developed for learning and practicing **Full Stack development**, using **Java + Spring Boot** for the back-end and **HTML, CSS, and JavaScript** for the front-end.

The system currently includes user authentication, registration, bank account management, balance checking, deposits, withdrawals, and integration between users and their bank accounts.

The application is being developed using a layered architecture, separating the responsibilities of each part of the system.

---

# 📋 Overview

The project currently contains the following main modules:

### 👤 User

Responsible for managing users.

Features:

* User registration
* Login
* Username and password validation
* Identification of the logged-in user
* Integration with the bank account
* Logout

**Status:** ✅ Implemented

---

### 🏦 Account

Responsible for managing bank accounts.

Features:

* Account creation
* Account lookup
* Balance lookup
* Account identification
* Deposits
* Withdrawals
* User-account integration

**Status:** ✅ Implemented

---

# 🏗️ Project Architecture

The application follows a layered architecture:

```text
Front-end
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
MySQL
```

Each layer has a specific responsibility.

---

## Front-end

The front-end is responsible for the visual interface and user interaction.

Technologies used:

* HTML5
* CSS3
* JavaScript

JavaScript communicates with the back-end API using the `fetch()` API.

---

## Controller

The Controller receives HTTP requests from the front-end.

Examples:

```text
POST /api/user/login
POST /api/user/register
POST /api/conta/depositar/{id}
POST /api/conta/sacar/{id}
GET  /api/account/me
```

The Controller should not contain all the business logic. Its main responsibility is to receive the request and forward the necessary information to the Service layer.

---

## Service

The Service layer contains the business logic of the application.

Examples:

* Checking whether a user exists
* Validating login credentials
* Creating users
* Creating accounts
* Processing deposits
* Checking the balance before withdrawals
* Updating the account balance

---

## Repository

The Repository layer is responsible for communication between the application and the database.

The project uses **Spring Data JPA**, allowing users and accounts to be stored and retrieved from the database.

---

# 🗄️ Database

The project currently uses:

**MySQL**

The database was created on a local machine for development purposes.

The database contains tables responsible for storing information about users and bank accounts.

Unlike the first version of the project, which used a `HashMap` to temporarily store users, the current version uses persistent database storage.

This means that data is not automatically lost when the application is restarted.

---

# 📁 Project Structure

The project can be organized approximately as follows:

```text
src/
└── main/
    ├── java/
    │   └── banck/
    │       └── client/
    │           └── kadu/
    │               ├── Controller/
    │               │   ├── UserController.java
    │               │   └── CountController.java
    │               │
    │               ├── services/
    │               │   ├── UserService.java
    │               │   └── CountService.java
    │               │
    │               ├── Repository/
    │               │   └── User.java
    │               │
    │               └── Request/
    │                   └── ValorRequest.java
    │
    └── resources/
        ├── userLogin.html
        ├── user.css
        └── index.html
```

> The structure may change as new classes and features are added to the project.

---

# 👤 User Module

The `User` module is responsible for user registration and authentication.

In the early version of the project, users were stored inside a `HashMap` in the `UserService`.

The project has since evolved to use **MySQL**, allowing user data to remain stored even after the application is stopped.

---

# 📝 User Registration

The user provides:

* Username
* Password
* Password confirmation

The front-end performs basic validation before sending the information to the API.

Examples of validations:

* Required fields
* Minimum password length
* Password confirmation
* Checking whether the username is already registered

The request is sent to:

```text
POST /api/user/register
```

---

# 🔐 Login

To access the system, the user provides their login credentials.

The front-end sends a request to:

```text
POST /api/user/login
```

The login data is sent using:

```text
application/x-www-form-urlencoded
```

After successful authentication, the user can access the main bank dashboard.

---

# 🔒 Authentication

The system has access control to prevent unauthorized users from accessing account information.

When the main page is loaded, the front-end communicates with the API to identify the currently authenticated user and retrieve their account information.

If the server returns:

```text
401 Unauthorized
```

the user is redirected to:

```text
userLogin.html
```

---

# 🏦 Account Module

The `Account` module is responsible for bank account operations.

Each account is associated with a registered user.

An account contains information such as:

* Account ID
* Account number
* Account balance
* Account owner

---

# 💰 Account Balance

The account balance is stored in the MySQL database.

When the user accesses the dashboard, the front-end requests the account information from the API.

The page displays:

```text
AVAILABLE BALANCE

R$ 0.00
```

The JavaScript code formats the value according to the Brazilian currency format.

For example:

```text
R$ 1,250.50
```

---

# ➕ Deposit

Users can make deposits using the:

```text
Deposit
```

button.

A modal window opens and asks the user to enter the amount.

The front-end sends the request to:

```text
POST /api/conta/depositar/{userId}
```

The amount is sent as JSON:

```json
{
    "valor": 100
}
```

The back-end processes the operation and updates the account balance.

After the deposit is completed, the new balance is automatically displayed on the page.

---

# ➖ Withdrawal

Users can also withdraw money using the:

```text
Withdraw
```

button.

A modal window asks the user to enter the desired amount.

The request is sent to:

```text
POST /api/conta/sacar/{userId}
```

The back-end processes the withdrawal and updates the balance.

If the operation cannot be completed, an error message is displayed to the user.

---

# 🔄 Banking Operation Flow

The basic flow of a deposit or withdrawal is:

```text
User
   ↓
Clicks "Deposit" or "Withdraw"
   ↓
Front-end opens the modal
   ↓
User enters the amount
   ↓
JavaScript sends HTTP request
   ↓
CountController
   ↓
CountService
   ↓
Repository
   ↓
MySQL
   ↓
Account balance is updated
   ↓
API returns the new balance
   ↓
Front-end updates the screen
```

---

# 🖥️ Main Dashboard

The `index.html` page works as the main dashboard of the user's bank account.

It displays several sections.

## Header

The header contains:

* Kadu Bank logo
* Logged-in user's email
* Logout button

---

## Welcome Section

The dashboard displays a personalized welcome message:

```text
Hello, user!

Welcome to Kadu Bank.
```

The displayed name is currently generated from the user's email address.

---

## Account Card

The account card displays:

* Available balance
* Account number
* Deposit button
* Withdrawal button

---

## Account Information

The information section displays:

* Email
* Account ID
* User ID

---

# 🚪 Logout

The user can leave their account using the:

```text
Logout
```

button.

The front-end sends a request to:

```text
POST /api/user/logout
```

After a successful logout, the user is redirected to:

```text
userLogin.html
```

---

# 🎨 User Interface

The Kadu Bank project has its own visual identity.

The interface uses:

* Dark background
* Dark gray surfaces
* Green as the primary color
* Red for error messages
* `Inter` font
* `Space Grotesk` font
* Rounded cards
* Gradients
* Hover effects
* Lightweight animations

The main green color is:

```css
--verde: #37C871;
```

A darker version can also be used for gradients:

```css
--verde-escuro: #229954;
```

Green is primarily used for:

* Logo
* Main buttons
* Active tabs
* Input focus
* Deposit buttons
* Confirmation buttons
* Success messages
* Highlight elements

---

# 📱 Responsive Design

The front-end supports different screen sizes.

CSS media queries are used to adapt the interface to smaller devices.

Examples:

```css
@media (max-width: 700px)
```

and:

```css
@media (max-width: 440px)
```

On smaller screens:

* The information grid becomes a single column
* The user's email can be hidden from the header
* Buttons are reorganized
* Text sizes are reduced
* Cards receive smaller spacing

---

# 🗄️ Local Database

The current MySQL database is configured on a local development machine.

This means that the application currently depends on the machine where the database is running.

For production or deployment, the database configuration will need to be changed to use a database server accessible by the deployed application.

---

# 🔄 Possible PostgreSQL Migration

One possible future improvement is migrating from **MySQL** to **PostgreSQL**.

The use of **Spring Data JPA** makes this migration easier because much of the database communication is abstracted by JPA and Hibernate.

The configuration would need to be changed, especially:

* Database dependency
* Database URL
* Username
* Password
* Spring database configuration
* Any database-specific SQL

The entity structure can remain mostly the same, depending on the implementation.

---

# 🔌 Main API Endpoints

The main endpoints currently used by the project include:

| Method | Route                           | Description                            |
| ------ | ------------------------------- | -------------------------------------- |
| `POST` | `/api/user/login`               | Logs the user into the system          |
| `POST` | `/api/user/register`            | Registers a new user                   |
| `POST` | `/api/user/logout`              | Logs the user out                      |
| `GET`  | `/api/account/me`               | Retrieves the logged-in user's account |
| `POST` | `/api/conta/depositar/{userId}` | Makes a deposit                        |
| `POST` | `/api/conta/sacar/{userId}`     | Makes a withdrawal                     |

> Endpoints may change as the back-end architecture continues to evolve.

---

# 🔗 Front-end → Back-end Communication

The front-end uses JavaScript's `fetch()` API to communicate with the back-end.

For example:

```javascript
const response = await fetch('/api/account/me');
```

For operations that modify data:

```javascript
const response = await fetch(
    `/api/conta/depositar/${userId}`,
    {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            valor: valor
        })
    }
);
```

The API processes the request and returns the result to the browser.

---

# 📊 Current Project Status

| Feature                     | Status                 |
| --------------------------- | ---------------------- |
| Login interface             | ✅ Implemented          |
| User registration           | ✅ Implemented          |
| Login                       | ✅ Implemented          |
| Logout                      | ✅ Implemented          |
| MySQL                       | ✅ Implemented          |
| User persistence            | ✅ Implemented          |
| Bank account                | ✅ Implemented          |
| Account lookup              | ✅ Implemented          |
| Balance lookup              | ✅ Implemented          |
| Deposit                     | ✅ Implemented          |
| Withdrawal                  | ✅ Implemented          |
| Front-end + API integration | ✅ Implemented          |
| Responsive interface        | ✅ Implemented          |
| PostgreSQL                  | ⏳ Possible improvement |
| Production database         | ⏳ Future               |
| Production deployment       | ⏳ Future               |

---

# 🚀 Future Improvements

The project is still under development and can receive several improvements.

Possible future features include:

* Improve authentication
* Improve session management
* Implement transaction history
* Implement transfers between accounts
* Add a simulated PIX system for educational purposes
* Add different account types
* Add more complete input validation
* Improve error handling
* Add automated tests
* Improve credential security
* Configure the database for production
* Deploy the back-end
* Use an external database server
* Evaluate migration from MySQL to PostgreSQL
* Create complete API documentation

---

# ⚠️ Security Notice

Kadu Bank is currently an educational and development project.

Some parts of the system still require additional security improvements before the application could be considered suitable for a real-world banking environment.

Areas that require attention include:

* Secure password storage
* Authentication
* Session management
* Authorization
* Input validation
* Route protection
* Database credential protection
* Protection against unauthorized requests

**This project must not be used for real banking transactions.**

---

# 📌 Conclusion

Kadu Bank started as a simple project using a `HashMap` to store users and has evolved into an application using **Spring Boot, JPA, MySQL, HTML, CSS, and JavaScript**.

The current system allows users to:

```text
Register
   ↓
Login
   ↓
Access their account
   ↓
Check their balance
   ↓
Make deposits
   ↓
Make withdrawals
   ↓
Update their balance
   ↓
Logout
```

The project is still under development, but its current architecture provides a solid foundation for adding new banking features without having to rebuild the entire application.
