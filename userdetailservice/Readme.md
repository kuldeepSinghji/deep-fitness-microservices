# User Detail Service

This microservice is part of the Deep Fitness platform and is responsible for managing user details, registration, and validation.

## Features

- Register new users
- Retrieve user details by user ID
- Validate users by user ID or Keycloak ID
- Integration with Keycloak for authentication
- RESTful API endpoints

## Technologies Used

- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- H2/PostgreSQL (configurable)
- Lombok
- JUnit & Mockito (for testing)
- Maven

## API Endpoints

| Method | Endpoint                        | Description                        |
|--------|---------------------------------|------------------------------------|
| GET    | `/api/users/{userId}`           | Get user details by user ID        |
| POST   | `/api/users/register-user`       | Register a new user                |
| GET    | `/api/users/validate/{userId}`   | Validate user by Keycloak ID       |

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven

### Running the Service

```bash
mvn clean install
mvn spring-boot:run
```

The service will start on `http://localhost:8080`.

### Example Request

**Register User**
```http
POST /api/users/register-user
Content-Type: application/json

{
  "email": "john.doe@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe"
}
```

## Testing

Run all tests with:
```bash
mvn test
```

## Project Structure

- `controller/` - REST controllers
- `service/` - Business logic
- `repository/` - Data access layer
- `model/` - Entity classes
- `dto/` - Data transfer objects
