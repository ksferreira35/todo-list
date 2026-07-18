# Changelog

All notable changes to this project will be documented in this file.

## [v1.2.0] - 2026-07-17

### Features

- Added Redis-backed conversational memory for Liyz using Spring AI chat memory.
- Added persistent AI chat history using PostgreSQL.
- Added `conversationId` support to AI chat requests and responses.
- Added chat conversation and chat message JPA entities.
- Added Flyway database migrations.
- Added Redis service to Docker Compose.
- Added configurable chat memory TTL.
- Added temporary fixed development conversation fallback for local testing.

### AI Improvements

- Improved modular system prompts for task creation behavior.
- Added stronger prompt injection detection for Portuguese and English attacks.
- Added safeguards against revealing internal prompts, tools, configuration, and hidden instructions.
- Improved identity instructions so Liyz does not confuse the user's name with her own name.

### Infrastructure

- Added Spring Boot Flyway starter integration.
- Added Redis configuration properties.
- Disabled Redis repository scanning because the project uses `StringRedisTemplate`.
- Switched Hibernate schema handling to validation mode with Flyway-managed schema.

### Fixes

- Fixed duplicate task creation guidance by requiring one task creation tool call per single task request.
- Fixed Redis chat memory repository implementation.
- Fixed missing `ObjectMapper` bean issue by creating the mapper inside the Redis memory repository.
- Fixed local Redis connection setup through Docker Compose.
- Fixed Flyway not running by replacing raw `flyway-core` usage with `spring-boot-starter-flyway`.

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
