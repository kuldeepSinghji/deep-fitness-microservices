# ConfigServer

This project is a Spring Cloud Config Server for the Deep Fitness Microservices ecosystem. It provides centralized external configuration for all microservices in the system.

## Features

- Centralized configuration management for microservices
- Supports native file system backend (YAML configuration files)
- Spring Cloud Config Server enabled
- Example configuration files for various services (API Gateway, Eureka, User Detail Service, User Activities, Deep AI Service)

## Project Structure

```
configServer/
├── src/
│   ├── main/
│   │   ├── java/com/deepfitness/configServer/ConfigServerApplication.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── config/
│   │           ├── deep-ai-service.yml
│   │           ├── deepfitness-api-gateway.yml
│   │           ├── eureka.yml
│   │           ├── user-activities.yml
│   │           └── user-detail-service.yml
│   └── test/
│       └── java/com/deepfitness/configServer/ConfigServerApplicationTests.java
├── pom.xml
└── README.md
```

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven

### Running the Config Server

1. Clone the repository.
2. Navigate to the `configServer` directory.
3. Build the project:

   ```sh
   ./mvnw clean install
   ```

4. Run the application:

   ```sh
   ./mvnw spring-boot:run
   ```

   The Config Server will start on port `8000` by default.

### Configuration

- All configuration files for microservices are located in `src/main/resources/config/`.
- The server uses the native file system backend as specified in [`application.yml`](src/main/resources/application.yml).

### Example Endpoints

- To fetch configuration for a service (e.g., `user-detail-service`):

  ```
  http://localhost:8000/user-detail-service/default
  ```

## Testing

Run tests with:

```sh
./mvnw test
```