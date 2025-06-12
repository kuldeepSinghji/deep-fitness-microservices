# User Activity Microservice

This microservice is part of the **Deep Fitness Microservices** architecture. It is responsible for tracking, storing, and retrieving user activity data such as workouts, calories burned, activity duration, and more.

## Features

- Register and store user activities (e.g., login, logout, workouts)
- Retrieve activities by user ID
- Validate user existence via external user service
- MongoDB for persistent storage
- RESTful API endpoints
- Global exception handling

## Tech Stack

- Java 21
- Spring Boot
- Spring Data MongoDB
- Spring WebFlux (for non-blocking HTTP calls)
- Lombok
- JUnit & Mockito for testing
- Maven

## API Endpoints

### Register User Activity

```
POST /api/activity/register-user-activity
```
**Request Body:**
```json
{
  "userId": "string",
  "activityId": "string",
  "activityDuration": 30,
  "caloriesBurned": 300,
  "activityStartTime": "2025-06-12T14:00:00"
}
```

### Get Activities by User ID

```
GET /api/activity/{userId}
```
**Response:**  
Returns a list of user activity records for the given user.

## Project Structure

- `controller/` — REST controllers
- `service/` — Business logic and service interfaces/implementations
- `repository/` — MongoDB repositories
- `model/` — Domain models (entities)
- `dto/` — Data transfer objects
- `exceptions/` — Custom exception classes
- `test/` — Unit and integration tests

## How to Run

1. Make sure MongoDB is running locally or update the connection string in `application.properties`.
2. Build the project:
    ```
    mvn clean install
    ```
3. Run the application:
    ```
    mvn spring-boot:run
    ```
4. Access the API at `http://localhost:8080/api/activity`

## Testing

Run all tests with:
```
mvn test
```

## Notes

- This service expects a running user service for user validation.
- All endpoints return standard HTTP status codes and error messages.

---

**Author:** Deep Fitness Team  