# Workgroup Final Project - Library REST API

Spring Boot REST API for managing library books and borrowings with JWT authentication and role-based authorization.

## Features

- Public user registration and login
- JWT stateless authentication
- Role-based authorization with `ADMIN` and `USER`
- Public book browsing
- Admin-only book create, update, and delete
- User borrowing management scoped to the authenticated user
- Admin access to all borrowings
- Swagger UI for API testing
- MySQL database support

## Tech Stack

- Java 25
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Spring Security
- MySQL
- JWT with `io.jsonwebtoken`
- Springdoc OpenAPI / Swagger UI
- Maven

## Project Structure

```text
src/main/java/com/final_project/workgroup_final_project
+-- controllers
+-- exceptions
+-- models
|   +-- records
+-- repos
+-- services
+-- OpenApiConfig.java
+-- SecurityConfig.java
+-- WorkgroupFinalProjectApplication.java
```

## Database

The local profile uses:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/masamune_ex
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```



The appuses `data.sql` to insert initial book data.

## Profiles

The main file is:

```text
src/main/resources/application.properties
```

It activates the local profile:

```properties
spring.profiles.active=local
```

The local runtime config is:

```text
src/main/resources/application-local.properties
```

## Run The App

```powershell
.\mvnw.cmd spring-boot:run
```

The local profile currently uses:

```text
http://localhost:8080
```

You can force the local profile with:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=local"
```

## Swagger UI

Open Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

### Using JWT In Swagger

1. Register or login using `/api/auth/register` or `/api/auth/login`.
2. Copy the `accessToken` value.
3. Click **Authorize** in Swagger UI.
4. Paste the token as:

```text
Bearer YOUR_TOKEN_HERE
```

5. Click **Authorize**.

Protected endpoints will then use your JWT.

## Authentication Endpoints

| Method | Endpoint | Access |
|---|---|---|
| POST | `/api/auth/register` | Public |
| POST | `/api/auth/login` | Public |

Register creates a `USER` account by default.

## Book Endpoints

| Method | Endpoint | Access |
|---|---|---|
| GET | `/api/books` | Public |
| GET | `/api/books/{id}` | Public |
| POST | `/api/books` | ADMIN |
| PUT | `/api/books/{id}` | ADMIN |
| DELETE | `/api/books/{id}` | ADMIN |

Public users can explore books without logging in.

## Borrowing Endpoints

| Method | Endpoint | Access |
|---|---|---|
| GET | `/api/borrowings` | USER or ADMIN |
| GET | `/api/borrowings/{id}` | USER or ADMIN |
| POST | `/api/borrowings` | USER or ADMIN |
| PUT | `/api/borrowings/{id}` | USER or ADMIN |
| DELETE | `/api/borrowings/{id}` | USER or ADMIN |

Borrowing visibility rules:

- `ADMIN` can see and manage all borrowings.
- `USER` can see and manage only their own borrowings.
- New borrowings are linked automatically to the authenticated user from the JWT.

## Authorization Summary

- Public users can register, login, and browse books.
- `USER` can manage only their own borrowings.
- `ADMIN` can manage books and all borrowings.

## JWT Configuration

Configured in `application-local.properties`:

```properties
app.jwt.secret=${JWT_SECRET:change-this-secret-key-change-this-secret-key}
app.jwt.expiration-ms=${JWT_EXPIRATION_MS:86400000}
```

The JWT contains:

- subject: user email
- claim: `role`
- expiration time

## Run Tests

```powershell
.\mvnw.cmd test
```
