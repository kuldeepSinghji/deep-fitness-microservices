# Deep Fitness API Gateway

This project is the **API Gateway** for the Deep Fitness Microservices architecture. It acts as a single entry point for all client requests, handling routing, security, and cross-cutting concerns for the backend microservices.

---

## Project Details

- **Purpose:**  
  Central gateway for routing, authentication, and security for Deep Fitness microservices.

- **Key Features:**
  - OAuth2/JWT authentication (Keycloak)
  - CORS configuration
  - Load-balanced WebClient for service-to-service communication
  - Custom filters for user synchronization
  - Spring Cloud integration for service discovery

---

## Technologies Used

- **Java:** 17+
- **Spring Boot:** 3.x
- **Spring WebFlux:** Reactive web framework
- **Spring Security:** OAuth2 Resource Server (JWT)
- **Spring Cloud:** Service discovery, load balancing
- **JUnit 5:** Unit testing
- **Mockito:** Mocking in tests
- **AssertJ:** Fluent assertions
- **Maven:** Build tool

---

## Main Modules

- `SpringConfig.java`  
  Configures security (JWT, OAuth2), CORS, and WebFlux security filter chain.

- `WebClientConfig.java`  
  Provides load-balanced `WebClient` beans for inter-service communication.

- `KeyCloakUserDetailsSyncFilter.java`  
  Custom WebFilter for synchronizing user details with Keycloak.

- `UserDetailsServiceImpl.java`  
  Service for user detail validation and registration via WebClient.

---

## Requirements

- **Java 17** or higher
- **Maven 3.8+**
- **Keycloak** (for OAuth2/JWT authentication)
- **Eureka** or compatible service discovery (if using service registry)

---

## Running the Project

1. **Build the project:**
   ```sh
   mvn clean install
   ```

2. **Run the application:**
   ```sh
   mvn spring-boot:run
   ```

3. **Run tests:**
   ```sh
   mvn test
   ```

---

## Project Structure

```
gateway/
├── src/
│   ├── main/
│   │   ├── java/com/deepfitness/gateway/config/         # Configuration classes
│   │   ├── java/com/deepfitness/gateway/keycloak/       # Keycloak filters
│   │   └── java/com/deepfitness/gateway/service/        # Service implementations
│   └── test/
│       └── java/com/deepfitness/gateway/                # Unit tests
├── pom.xml
└── README.md
```


