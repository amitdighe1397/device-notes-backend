# Device Notes Backend Feature

## Overview

This project implements a backend feature to store and retrieve notes for a device.

The implementation focuses on clean architecture, service-layer validation, database-level pagination, and schema version control.

Built using:

- Java 17
- Spring Boot 3
- Spring Data JPA
- Liquibase
- MySQL 8

---

## Architecture

The project follows clean layered architecture:

- **Controller** → Handles HTTP requests and response mapping  
- **Service** → Contains business logic, validation, and transaction management  
- **Repository** → Handles database access using JPA  

All business validation rules are implemented in the Service layer.

Liquibase is used for version-controlled database schema management.

---

## Database

### Table: `device_note`

| Column      | Type         | Description |
|------------|-------------|------------|
| id         | BIGINT       | Auto-generated primary key |
| device_id  | BIGINT       | Device identifier |
| note       | TEXT         | Device note (max 1000 characters enforced at API level) |
| created_at | TIMESTAMP    | Creation timestamp |
| created_by | VARCHAR(100) | User who created the note |

Liquibase automatically creates the table during application startup.

---

## API Endpoints

### 1️⃣ Create Note

**POST**  
`/api/v1/devices/{deviceId}/notes`

### Required Header


X-User: username


### Request Body

```json
{
  "note": "Device rebooted during maintenance"
}
Response
{
  "id": 1,
  "deviceId": 1,
  "note": "Device rebooted during maintenance",
  "createdAt": "2026-02-26T22:46:49",
  "createdBy": "Amit"
}
2️⃣ Get Device Notes

GET
/api/v1/devices/{deviceId}/notes?limit=20

Query Parameter

limit (default: 10, max: 100)

Response
[
  {
    "id": 1,
    "deviceId": 1,
    "note": "Device rebooted during maintenance",
    "createdAt": "2026-02-26T22:46:49",
    "createdBy": "Amit"
  }
]

Results are sorted by createdAt in descending order.

Validation Rules

Note must not be blank

Note maximum length: 1000 characters

X-User header is mandatory

Limit must be between 1 and 100

Validation is implemented in the Service layer.

Technical Highlights

Clean separation of Controller, Service, and Repository layers

Service-layer business validation

Database-level pagination and sorting

Liquibase-managed schema

Global exception handling

Structured logging

Assumptions

A device table was not provided in the environment.

Therefore, no foreign key constraint was added.

MySQL was used for local development.

How to Run the Application

Create a MySQL database:

CREATE DATABASE devicedb;

Update application.yaml with your database credentials.

Run the application:

mvn spring-boot:run

Liquibase will automatically create the required tables.

Part A – Architecture Warm-up (Written)
1️⃣ Responsibilities of Controller, Service, and Repository layers

The Controller handles HTTP requests, request mapping, and response formatting.
The Service layer contains business logic, validation rules, and transaction management.
The Repository layer is responsible only for data access and database interaction.
This separation improves maintainability, clarity, and testability.

2️⃣ What is a database transaction? When should @Transactional be used or avoided?

A database transaction is a unit of work that ensures ACID properties (Atomicity, Consistency, Isolation, Durability).
@Transactional should be used in the Service layer when multiple database operations must succeed or fail together.
It should be avoided in Controllers and in simple read-only queries unless necessary.

3️⃣ Why are DTOs used instead of returning JPA entities directly from APIs?

DTOs decouple API contracts from internal database entities.
They prevent accidental exposure of unnecessary fields.
DTOs also help avoid lazy loading issues and protect internal domain structure from external changes.

4️⃣ What problem does Liquibase solve in a multi-developer / multi-environment setup?

Liquibase provides version-controlled database schema management.
It ensures consistent schema across multiple developers and environments.
It prevents manual SQL drift and allows controlled schema evolution through changelogs.