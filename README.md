# Banking Management System – Java Programming Final Project

## Project Description
This project is a RESTful Banking Management System developed using Java and Spring Boot.  
It allows managing banks, customers, accounts, transactions, and cards with full CRUD functionality and business logic.

The system supports deposits, withdrawals, transaction tracking, and card issuance, following a clean layered architecture.

---

## Technologies Used
- Java 17+
- Spring Boot
- Spring Data JPA
- SQL Server
- Maven
- JUnit 5
- Postman
- Git & GitHub

---

## Project Structure
The application follows a layered architecture:

- Controller – Handles HTTP requests
- Service – Contains business logic
- Repository – Handles database operations
- DTO – Data Transfer Objects for API communication
- Entity – Database entities
- Mapper – Converts between Entity and DTO
- Tests – Unit tests using JUnit 5

---

## Entities
- Bank
- Customer
- Account
- Transaction
- Card

Each account belongs to a customer and a bank.  
Each transaction is linked to an account.  
Each card is issued for an account.

---

## Features
- Create and manage banks
- Register customers
- Create bank accounts
- Deposit and withdraw money
- Automatically record transactions
- View transaction history per account
- Issue cards for accounts
- Unit testing for core business logic

---

## REST API Endpoints

### Bank
- POST /api/banks
- GET /api/banks

### Customer
- POST /api/customers
- GET /api/customers

### Account
- POST /api/accounts
- GET /api/accounts
- PUT /api/accounts/{id}/deposit
- PUT /api/accounts/{id}/withdraw

### Transaction
- GET /api/transactions/account/{accountId}

### Card
- POST /api/cards
- GET /api/cards/account/{accountId}

---

## Unit Testing
Unit tests are implemented using JUnit 5 and are located in:

src/test/java/net/javaguides/banking/tests

---

## Authors
Sanie Adili  
Urata Hyseni
