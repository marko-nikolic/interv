# InterV tender management application

## About application
Application was developed as Java 11 Spring Boot application,
with embeded H2 database and it comes with provided gradle wrapper so no additional dependencies are required to build project.
Database migration is being handled by liquibase. Persistence layer is implemented by using Spring Data JPA.

Codebase is split into api, commons and core packages. 
Coommons package contains utility classes and common models. 
In api package you can find: domain models, endpoints and command/query objects (leaning toward CQRS approach).
Core package contains business services, repositories, entities and mappers (used for mapping between entities nad model classes)

Tests are written as spring integration tests (end-to-end), and api is documented by using Spring Rest Docs.

## Building and running  application
In root folder execute `gradlew build`. You can run application in several ways, but the easier is to also use gradle wrapper with command `gradlew bootRun`

## API Documentation
There are several ways to learn about API and test it. If the application is running, you can check http://localhost:8080/interv/docs/index.html where you can find docs explaining available endpoints, and request/responses for each service.

You can also find same document in pdf format in `src/docs/assciidoc` (No need to run application)

For easier testing, postamn collection is also provided as part of the code, in location `sec/docs/postman`. Simple import collection in you postman and start using it.  