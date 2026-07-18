# Todo List API with Spring AI

A modern task management API built with **Spring Boot** and enhanced with **Spring AI**.

The project includes **Liyz**, an AI assistant capable of creating, organizing, and managing tasks through natural language. Using conversational memory and tool calling, Liyz can interact with your task list instead of simply generating text.

> **Note:** This project is currently under active development.

---

## Preview

### AI Chat (Front-end)

> *(Screenshot coming soon)*

### Swagger

![Swagger](/src/main/resources/docs/swagger.png)

---

## Features

- AI-powered task management with Spring AI
- Conversational memory using Redis
- Persistent AI chat history using PostgreSQL
- CRUD operations for tasks
- PostgreSQL database
- Flyway database migrations
- Docker Compose environment for PostgreSQL, Redis, and pgAdmin
- RESTful API
- OpenAPI / Swagger documentation
- Modular AI prompt architecture
- Spring AI Tool Calling
- Prompt injection protection advisor

---

## Project Structure

| Directory | Description |
|-----------|-------------|
| `config` | Spring Boot configuration classes. |
| `controller` | REST API endpoints. |
| `dto` | Request and response objects. |
| `entity` | JPA entities. |
| `exception` | Global exception handling using `ProblemDetail`. |
| `prompt` | Prompt loader and AI prompt management. |
| `repository` | Spring Data JPA repositories and Redis chat memory repository. |
| `service` | Business logic implementation. |
| `tools` | Spring AI Tool Calling functions. |
| `resources/db/migration` | Flyway SQL migrations. |
| `resources/prompts` | Modular AI system prompts. |

---

## Available Endpoints

### Task API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/tasks` | Get all tasks |
| POST | `/api/tasks` | Create a new task |
| GET | `/api/tasks/{id}` | Get a task by ID |
| PATCH | `/api/tasks/{id}` | Update task completion status |
| DELETE | `/api/tasks/{id}` | Delete a task |

### AI API

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/ai/chat` | Chat with Liyz using natural language |

---

## AI Capabilities

Liyz is able to:

- Create tasks from natural language.
- Organize daily and weekly routines.
- Update existing tasks.
- Maintain conversational context using Redis.
- Persist chat history using PostgreSQL.
- Call backend tools through Spring AI Tool Calling.
- Respond using Markdown for improved readability.

---

## Technologies

- Java 21
- Spring Boot
- Spring AI
- PostgreSQL
- Redis
- Flyway
- Docker / Docker Compose
- Maven
- Swagger / OpenAPI

---

## Getting Started

### Prerequisites

Before running the project, make sure you have:

- Java 21+
- Docker and Docker Compose
- Maven
- An OpenAI or Groq API Key

---

### Environment Variables

Copy the example file:

```bash
cp .env.example .env
```

Configure:

- PostgreSQL credentials
- Redis connection
- OpenAI or Groq API Key
- Chat memory settings

For local development outside Docker, use `REDIS_HOST=localhost` and the
PostgreSQL port exposed by Docker Compose.

---

### Running the Project

```bash
git clone https://github.com/ksferreira35/todo-list.git

cd todo-list

docker compose up -d database redis pgadmin

mvn spring-boot:run
```

---

## Roadmap

- [x] CRUD API
- [x] Spring AI Integration
- [x] Spring AI Tool Calling
- [x] Conversational Memory (Redis)
- [x] Persistent Chat History (PostgreSQL)
- [x] Modular Prompt Architecture
- [x] Flyway Database Migrations
- [x] Swagger / OpenAPI Documentation
- [x] Docker Compose

### Next Features

- [ ] Unit Tests
- [ ] Integration Tests
- [ ] Frontend conversation flow using returned `conversationId`
- [ ] Replace temporary fixed development conversation fallback
- [ ] Authentication & Authorization (Spring Security)
- [ ] User-scoped tasks and chat conversations
- [ ] Due Dates, Reminders & Notifications
- [ ] HTMX + Thymeleaf Frontend

> The roadmap represents the planned core features. The project will continue to evolve with new improvements after these milestones are completed.

---

## License

This project is licensed under the MIT License.

See the [LICENSE](LICENSE) file for more information.
