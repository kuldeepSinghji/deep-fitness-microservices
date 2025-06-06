# Eureka Service Registry

This project is a Spring Boot application that serves as a Eureka Service Registry for the Deep Fitness Microservices architecture. It enables service discovery and registration, allowing microservices to find and communicate with each other dynamically.

## Features

- **Eureka Server**: Central registry for microservices.
- **Spring Cloud Config Client**: Loads configuration from a centralized config server.
- **Java 21**: Built with the latest LTS version of Java.
- **Spring Boot 3.4.5**: Modern Spring Boot application.

## Getting Started

Follow these steps to set up and run the Eureka Service Registry:

### Prerequisites

- **Java 21+**: Ensure Java is installed and `JAVA_HOME` is set.
- **Maven 3.9+**: Required for building and running the project.
- **Spring Cloud Config Server**: Should be running at `http://localhost:8000` or update the config URL as needed.

### Running the Application

Start the Eureka server using Maven Wrapper: the Eureka server using Maven Wrapper:




```./mvnw spring-boot:run```sh```sh
./mvnw spring-boot:run