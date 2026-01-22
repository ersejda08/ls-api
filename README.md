# LearnSmartly API

## Project Overview

LearnSmartly API is a Spring Boot REST backend service for an online learning platform. It provides authentication, course management, and enrollment functionality with JWT-based security and role-based access control.

## Features

- **User Authentication**: Register and login with JWT token generation
- **Course Management**: Teachers can create, read, update, and delete courses
- **Enrollment System**: Students can enroll in courses
- **Role-Based Access Control**: STUDENT and TEACHER roles with restricted endpoints
- **Secure API**: JWT authentication with Spring Security
- **Database**: PostgreSQL with Hibernate ORM
- **API Documentation**: Swagger/OpenAPI integration

## Tech Stack

- Java 21
- Spring Boot 4.0.1
- Spring Security with JWT
- Spring Data JPA / Hibernate
- PostgreSQL 18
- Maven
- Tomcat (embedded)

## Prerequisites

- Java 21+
- Maven 3.8+
- PostgreSQL 13+ running locally
- Port 8080 available (or configure a different port in `application.properties`)

## Setup & Running

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd ls-api
   ```

2. **Create PostgreSQL database**
   ```bash
   createdb -U postgres ls
   ```

3. **Update database credentials** (if different)
   - Edit `src/main/resources/application.properties`
   - Update `spring.datasource.username` and `spring.datasource.password`

4. **Build and run**
   ```bash
   ./mvnw clean spring-boot:run
   ```

5. **Access the application**
   - API Base URL: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`

## API Endpoints

- `POST /api/auth/register` - Register new user (default role: STUDENT)
- `POST /api/auth/login` - Login and get JWT token
- `GET /api/auth/me` - Get current user info (requires JWT)
- `GET /api/courses` - List all courses (requires JWT)
- `POST /api/courses` - Create course (TEACHER only)
- `PUT /api/courses/{id}` - Update course (TEACHER only)
- `DELETE /api/courses/{id}` - Delete course (TEACHER only)
- `GET /api/enrollments` - Get user enrollments (requires JWT)

## Authorization

- JWT token required in `Authorization: Bearer <token>` header for all protected endpoints
- Course management endpoints restricted to TEACHER role
- Students can view courses and enroll
- Default role for new registrations is STUDENT
