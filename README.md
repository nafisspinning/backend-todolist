# Todolist Backend (Spring Boot + PostgreSQL)

REST API for the project/task management frontend.

## Stack
- Java 21, Spring Boot 3.3 (Web, Data JPA, Validation, Actuator)
- PostgreSQL 16 + Flyway migrations
- springdoc-openapi (Swagger UI at `/swagger-ui.html`)

## Run locally

```bash
cp .env.example .env        # then edit values
docker compose up -d db     # start only Postgres
export $(cat .env | xargs)  # or use an IDE run config with these env vars
mvn spring-boot:run
```

Or run everything (API + DB) with Docker:

```bash
docker compose up --build
```

API will be available at `http://localhost:8080/api/v1`.
Health check: `http://localhost:8080/actuator/health`.
Swagger UI: `http://localhost:8080/swagger-ui.html`.

## Endpoints

| Method | Path                                        | Description              |
|--------|---------------------------------------------|---------------------------|
| GET    | /api/v1/projects                            | List all projects         |
| GET    | /api/v1/projects/{id}                       | Get one project           |
| POST   | /api/v1/projects                            | Create a project          |
| PUT    | /api/v1/projects/{id}                       | Update a project          |
| DELETE | /api/v1/projects/{id}                       | Delete a project (+tasks) |
| GET    | /api/v1/projects/{projectId}/tasks          | List tasks in a project   |
| POST   | /api/v1/projects/{projectId}/tasks          | Add a task                |
| PATCH  | /api/v1/projects/{projectId}/tasks/{id}/toggle | Toggle task done        |
| DELETE | /api/v1/projects/{projectId}/tasks/{id}     | Delete a task             |

## Tests

```bash
mvn test
```

Tests run against an in-memory H2 database (`test` profile), no Postgres required.

## Build a production jar

```bash
mvn clean package -DskipTests
java -jar target/todolist-backend-1.0.0.jar
```
