# Opsifi Job Agent Platform

A Job Search Multi-Agent Platform backend foundation for managing candidate job-search workflows. This initial implementation focuses on Candidate Profile Management CRUD APIs only.

## Tech Stack

- Java 21
- Spring Boot 3.x
- Maven
- PostgreSQL
- Spring Web
- Spring Data JPA
- Bean Validation
- Lombok

## Start PostgreSQL with Docker Compose

```bash
docker compose up -d
```

This starts PostgreSQL 16 with:

- Database: `job_agent_db`
- Username: `postgres`
- Password: `postgres`
- Port: `5432`
- Persistent volume: `postgres_data`

To stop PostgreSQL:

```bash
docker compose down
```

## Run the Backend Locally

Ensure Java 21 and Maven are installed, then run:

```bash
mvn spring-boot:run
```

The backend uses these datasource defaults, which can be overridden with environment variables:

```yaml
DB_URL=jdbc:postgresql://localhost:5432/job_agent_db
DB_USERNAME=postgres
DB_PASSWORD=postgres
```

## Build and Test

```bash
mvn clean test
```

## Sample API Endpoints

Base URL: `http://localhost:8080`

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/api/candidates` | Create a candidate profile |
| `GET` | `/api/candidates/{id}` | Get a candidate profile by id |
| `GET` | `/api/candidates` | Get all candidate profiles |
| `PUT` | `/api/candidates/{id}` | Update a candidate profile |
| `DELETE` | `/api/candidates/{id}` | Delete a candidate profile |

### Create Candidate Profile Example

```bash
curl -X POST http://localhost:8080/api/candidates \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Jane Doe",
    "email": "jane.doe@example.com",
    "phone": "+1-555-0100",
    "currentTitle": "Software Engineer",
    "currentCompany": "Example Inc",
    "currentCtc": 120000,
    "expectedCtc": 150000,
    "totalExperienceYears": 5,
    "noticePeriod": "30 days",
    "preferredLocations": ["Remote", "New York"],
    "targetRoles": ["Senior Software Engineer"],
    "primarySkills": ["Java", "Spring Boot", "PostgreSQL"],
    "secondarySkills": ["React", "Docker"],
    "summary": "Backend engineer focused on clean, scalable services."
  }'
```
