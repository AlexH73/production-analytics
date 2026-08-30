# Production Analytics

<p>
  <a href="https://www.oracle.com/java/"><img src="https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white" alt="Java 21"></a>
  <a href="https://spring.io/projects/spring-boot"><img src="https://img.shields.io/badge/Spring_Boot-3.3.4-6DB33F?logo=springboot&logoColor=white" alt="Spring Boot 3.3.4"></a>
  <a href="https://maven.apache.org/"><img src="https://img.shields.io/badge/Maven-C71A36?logo=apachemaven&logoColor=white" alt="Maven"></a>
  <a href="https://www.h2database.com/"><img src="https://img.shields.io/badge/H2-Database-09476B" alt="H2 Database"></a>
  <a href="https://documentation.red-gate.com/fd"><img src="https://img.shields.io/badge/Flyway-CC0200?logo=flyway&logoColor=white" alt="Flyway"></a>
  <a href="https://junit.org/junit5/"><img src="https://img.shields.io/badge/JUnit_5-25A162?logo=junit5&logoColor=white" alt="JUnit 5"></a>
  <a href="https://site.mockito.org/"><img src="https://img.shields.io/badge/Mockito-Testing-78A641" alt="Mockito"></a>
  <a href="LICENSE"><img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="MIT License"></a>
</p>

Production Analytics is a full-stack portfolio project for recording and analyzing events from manufacturing processes.

The project demonstrates how production events—such as machine starts, stops, downtime, and completed operations—can be collected through a REST API, stored in a database, and later presented in a React dashboard.

The main goal is to replace scattered manual records with structured production data that can be used for process transparency, downtime analysis, and operational decision-making.

The system starts with one production area but is designed so that it can later be extended to multiple machines, production lines, departments, and event types.

## Current Features

- Create production events through a REST API
- Retrieve all events or a single event by ID
- Filter events by type
- Validate incoming requests
- Return structured API errors
- Separate API DTOs from database entities
- Store events using Spring Data JPA
- Manage the database schema with Flyway
- Unit and integration tests

## Planned Features

- PostgreSQL database
- Machines and production lines
- Event categories and production statuses
- Date, machine, and event-type filters
- Downtime and production-duration calculations
- Production statistics and KPIs
- React dashboard with tables and charts
- Frontend and backend integration
- Docker-based local environment
- Authentication and role-based access
- API and project documentation

## Technology Stack

### Backend

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Bean Validation
- Flyway
- H2
- JUnit 5
- Mockito
- Maven

### Frontend

The React frontend will be added in a later development stage.

## Project Purpose

This project combines software development with real manufacturing process knowledge. It is being developed incrementally as a production-oriented full-stack application and as a demonstration of practical experience with Java, Spring Boot, REST APIs, databases, testing, and React.
