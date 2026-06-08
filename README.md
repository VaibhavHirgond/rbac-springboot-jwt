# RBAC Spring Boot JWT Authentication System

A Role-Based Access Control (RBAC) system built using Spring Boot, Spring Security, JWT Authentication, MySQL, and JPA.

This project demonstrates secure authentication and authorization using Roles and Permissions with JWT tokens.

---

## Features

- User Authentication using JWT
- Role-Based Access Control (RBAC)
- Permission-Based Authorization
- Secure Password Encryption using BCrypt
- Spring Security Integration
- REST APIs for User, Role, and Permission Management
- MySQL Database Integration
- CRUD Operations
- Protected Endpoints using JWT Tokens

---

## Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT (JSON Web Token)
- Maven

### Database
- MySQL

### Tools
- Postman
- Git
- GitHub
- VS Code

---

## Project Structure

src/main/java/com/rbac/rbac_backend

├── controller

├── entity

├── repository

├── security

├── service

└── dto

---

## Authentication Flow

1. User logs in using username and password.
2. Server validates credentials.
3. JWT token is generated.
4. User sends JWT token in Authorization header.
5. Spring Security validates JWT token.
6. Access is granted based on assigned roles and permissions.

---

## Roles

### ADMIN

Permissions:

- CREATE_USER
- READ_USER
- UPDATE_USER
- DELETE_USER

### USER

Permissions:

- READ_USER

---

## API Endpoints

### Authentication

#### Login

```http
POST /auth/login
```

Request:

```json
{
  "username": "admin",
  "password": "12345"
}
```

Response:

```json
{
  "token": "jwt-token"
}
```

---

### Users

#### Create User

```http
POST /users
```

#### Get All Users

```http
GET /users
```

Permission Required:

```text
READ_USER
```

#### Update User

```http
PUT /users/{id}
```

Permission Required:

```text
UPDATE_USER
```

#### Delete User

```http
DELETE /users/{id}
```

Permission Required:

```text
DELETE_USER
```

---

## Database Tables

### users

- id
- username
- password

### roles

- id
- name

### permissions

- id
- name

### user_roles

### role_permissions

---

## Security

Passwords are encrypted using BCrypt before storing in the database.

Example:

```text
$2a$10$...
```

JWT tokens are verified for every protected request using a custom JWT filter.

---

## How to Run

### Clone Repository

```bash
git clone https://github.com/VaibhavHirgond/rbac-springboot-jwt.git
```

### Navigate to Project

```bash
cd rbac-springboot-jwt
```

### Configure Database

Update:

```properties
application.properties
```

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/rbac_db
spring.datasource.username=root
spring.datasource.password=yourpassword
```

### Run Application

Windows:

```bash
.\mvnw.cmd spring-boot:run
```

Linux/Mac:

```bash
./mvnw spring-boot:run
```

Application runs on:

```text
http://localhost:8080
```

---

## Author

Vaibhav Hirgond

GitHub:
https://github.com/VaibhavHirgond

---

## Future Enhancements

- User Registration API
- Refresh Tokens
- Global Exception Handling
- Request Validation
- Swagger Documentation
- Admin Dashboard