# Deep AI Service

This service is a core component of the Deep Fitness Microservices ecosystem. It provides AI-powered recommendations and analytics for user fitness activities, leveraging Google Gemini and RabbitMQ for asynchronous processing.

## Features

- **AI Recommendations:** Generates personalized fitness recommendations using Google Gemini API.
- **User Activity Processing:** Listens to user activity events via RabbitMQ and processes them asynchronously.
- **REST API:** Exposes endpoints to fetch AI recommendations for users and activities.
- **Microservice Architecture:** Designed to be a part of a scalable, distributed fitness platform.

## Main Technologies

- Java 17+
- Spring Boot
- Spring AMQP (RabbitMQ)
- Spring WebFlux (for non-blocking HTTP)
- Google Gemini API (LLM)
- Lombok
- JUnit 5 & Mockito (for testing)

## Project Structure

- `controller/` - REST controllers for API endpoints
- `service/` - Business logic, AI integration, and message listeners
- `model/` - Domain models (UserActivity, AIRecommendations, etc.)
- `repository/` - Data access interfaces
- `config/` - Configuration classes (RabbitMQ, etc.)

## How It Works

1. **User Activity Event:** Other services publish user activity data to RabbitMQ.
2. **Message Listener:** `UserActivityMessageListenerImpl` consumes these messages.
3. **AI Recommendation:** The listener invokes `UserActivityAIServiceImpl`, which calls Gemini API and parses the response.
4. **Persistence:** AI recommendations are saved to the database via `AIRecommendationsRepository`.
5. **API Access:** Clients can fetch recommendations via REST endpoints.

## Running the Service

1. **Prerequisites:**  
   - Java 17+  
   - RabbitMQ instance  
   - Google Gemini API key

2. **Configuration:**  
   Set the following environment variables or application properties:
   - `GEMINI_API_URL`
   - `GEMINI_API_KEY`
   - RabbitMQ connection details

3. **Build & Run:**  
   ```sh
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

## Testing

Run all tests with:
```sh
./mvnw test
```

## Example API Usage

- **Get Recommendations for a User:**
  ```
  GET /api/recommendations/{userId}
  ```

- **Get Recommendation for an Activity:**
  ```
  GET /api/recommendations/activity/{activityId}
  ```

## Contributing

Pull requests are welcome. Please add tests for new features and ensure the build passes.

