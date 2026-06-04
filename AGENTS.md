## Project Context

This repository contains a Job Search Multi-Agent Platform.

The platform helps candidates manage job search workflows such as profile management, resume analysis, job matching, outreach, follow-ups, interview preparation, and application tracking.

## Tech Stack

- Java 21
- Spring Boot 3.x
- Maven
- PostgreSQL
- React
- LangChain4j

## General Rules

- Keep implementation simple, clean, and production-style.
- Implement only what is explicitly requested in the current task prompt.
- Do not add future features unless requested.
- Do not over-engineer.
- Prefer readable code over clever code.
- Keep changes small and reviewable.
- Do not mix unrelated features in one change.

## Backend Rules

- Use base package: `com.jobagent`.
- Use feature-based packages.
- Use DTOs for API request and response.
- Do not expose JPA entities directly from controllers.
- Use UUID as primary key for main domain entities.
- Add `createdAt` and `updatedAt` fields for persistent entities.
- Use Bean Validation for request validation.
- Use constructor injection.
- Keep business logic inside service classes.
- Keep controllers thin.
- Use meaningful exception handling.

## Database Rules

- PostgreSQL is the primary database.
- Use clear table names.
- Avoid destructive schema changes unless explicitly requested.
- Prefer migrations later when Flyway/Liquibase is introduced.

## API Rules

- REST APIs should use `/api/...` prefix.
- Use proper HTTP status codes.
- Use consistent error response format.
- Validate input at API boundary.

## Frontend Rules

- React frontend will be implemented separately.
- Keep frontend changes separate from backend changes unless explicitly requested.
- Use simple, readable component structure.

## AI Rules

- LangChain4j will be used for AI features.
- Do not add AI integration unless the task explicitly asks for it.
- Keep prompts and agent behavior configurable where practical.

## Testing Rules

- Add tests when requested or when business logic is added.
- Ensure the project builds successfully before completing a task.
- Do not ignore failing tests.

## Git Rules

- Work in small focused changes.
- Use meaningful commit messages.
- Do not reformat unrelated files.
- Do not introduce secrets, tokens, or credentials.
