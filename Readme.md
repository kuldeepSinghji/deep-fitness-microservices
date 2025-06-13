# Deep Fitness Microservices

This repository contains a set of microservices designed for the Deep Fitness platform. Each microservice is responsible for a specific domain within the fitness ecosystem, allowing for modular, scalable, and maintainable development.

---

## Microservices Overview
# User Detail Service

This microservice is part of the Deep Fitness platform and is responsible for managing user details, registration, and validation.

## Features

- Register new users
- Retrieve user details by user ID
- Validate users by user ID or Keycloak ID
- Integration with Keycloak for authentication
- RESTful API endpoints

---

# User Activity Microservice

This microservice is part of the **Deep Fitness Microservices** architecture. It is responsible for tracking, storing, and retrieving user activity data such as workouts, calories burned, activity duration, and more.

## Features

- Register and store user activities (e.g., login, logout, workouts)
- Retrieve activities by user ID
- Validate user existence via external user service
- MongoDB for persistent storage
- RESTful API endpoints
- Global exception handling

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

# Eureka Service Registry

This project is a Spring Boot application that serves as a Eureka Service Registry for the Deep Fitness Microservices architecture. It enables service discovery and registration, allowing microservices to find and communicate with each other dynamically.

## Features

- **Eureka Server**: Central registry for microservices.
- **Spring Cloud Config Client**: Loads configuration from a centralized config server.
- **Java 21**: Built with the latest LTS version of Java.
- **Spring Boot 3.4.5**: Modern Spring Boot application.

# Deep Fitness UI

A modern fitness tracking web application built with **React**, **TypeScript**, and **Vite**.  
This project is part of the Deep Fitness microservices suite.

---
# ConfigServer

This project is a Spring Cloud Config Server for the Deep Fitness Microservices ecosystem. It provides centralized external configuration for all microservices in the system.

## Features

- Centralized configuration management for microservices
- Supports native file system backend (YAML configuration files)
- Spring Cloud Config Server enabled
- Example configuration files for various services (API Gateway, Eureka, User Detail Service, User Activities, Deep AI Service)

## Technologies Used

- **Backend:** Node.js, Express
- **Database:** MongoDB
- **Authentication:** JWT
- **Containerization:** Docker (optional)
- **API:** RESTful

# Deep AI Service

This service is a core component of the Deep Fitness Microservices ecosystem. It provides AI-powered recommendations and analytics for user fitness activities, leveraging Google Gemini and RabbitMQ for asynchronous processing.

## Features

- **AI Recommendations:** Generates personalized fitness recommendations using Google Gemini API.
- **User Activity Processing:** Listens to user activity events via RabbitMQ and processes them asynchronously.
- **REST API:** Exposes endpoints to fetch AI recommendations for users and activities.
- **Microservice Architecture:** Designed to be a part of a scalable, distributed fitness platform.

---

## Getting Started

1. **Clone the repository:**
   ```sh
   git clone https://github.com/yourusername/deep-fitness-microservices.git
   cd deep-fitness-microservices
   ```

2. **Install dependencies for each microservice:**
   ```sh
   cd <microservice-folder>
   npm install
   ```

3. **Configure environment variables:**
   - Copy `.env.example` to `.env` in each microservice and update the values as needed.

4. **Run the services:**
   ```sh
   npm start
   ```
   Or use Docker Compose if available:
   ```sh
   docker-compose up --build
   ```

---

## Folder Structure

```
deep-fitness-microservices/
│
├── user-service/
├── workout-service/
├── nutrition-service/
├── analytics-service/
└── Readme.md
```

---

## Contributing

1. Fork the repository.
2. Create a new branch for your feature or bugfix.
3. Commit your changes and open a pull request.
