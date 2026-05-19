# Library REST API

## Authentication and Authorization

- JWT is used for stateless authentication.
- The token contains the user email and role.
- RBAC protects endpoints using ADMIN and USER roles.
- ADMIN can perform all book CRUD operations.
- USER can see books and borrow books.

## Database

- Run `src/main/resources/create_database.sql` in MySQL Workbench to create the `masamune` database and required tables.
- The script uses `CREATE DATABASE IF NOT EXISTS` and `CREATE TABLE IF NOT EXISTS`, so it does not delete existing data.
