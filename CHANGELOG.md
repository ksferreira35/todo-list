# Changelog

All notable changes to this project will be documented in this file.

## [v1.0.0] - 2026-07-13 (Initial Release)

### Endpoints

- GET /api/tasks — List all tasks
- GET /api/tasks/{id} — Get a task by ID
- POST /api/tasks — Create a new task
- PATCH /api/tasks/{id} — Update a task completion status
- DELETE /api/tasks/{id} — Delete a task by ID

### Features

- Task CRUD API
- Request, Response, and Status DTOs
- Pagination support
- PostgreSQL integration
- OpenAPI/Swagger documentation
- Global exception handling with `@RestControllerAdvice`
- Standardized error responses using `ProblemDetail`
