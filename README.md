# Shopping Platform Service

## Project Overview

This project implements a part of a shopping platform, providing services for managing products (commodities) and calculating their prices with discounts. The system is built using Spring Boot, Java 17, and employs modern design patterns, including:

- Strategy Pattern for calculating discounts.
- Configurable discount policies using configuration files.
- A modular architecture with a clear separation of concerns.

## Core Features

- Adding and retrieving products.
- Calculating discounts based on configurable discount policies.
- Calculating the final price of a product after applying the discount.

## Technology Stack

The project uses the following technologies and tools:

- Java 17: Utilizes modern language features, including records and improved collection handling.
- Spring Boot 3: Provides a fast and flexible framework for building REST APIs with auto-configuration.
- Lombok: Reduces boilerplate code (getters, setters, constructors) for more concise and clean code.
- MapStruct: Used for mapping objects (DTO ↔ Entity), enhancing performance and code readability by reducing manual mapping.
- PostgreSQL: A relational database used for data persistence, which is run using Testcontainers during integration testing.
- Testcontainers: Allows running a real PostgreSQL database in a Docker container, enabling reliable and isolated integration tests.
- Docker & Docker Compose: Containerizes the application and the database to ensure consistency across different environments.
- Swagger (OpenAPI): Provides automatic API documentation, making endpoint exploration and testing easier.

## Design Patterns

### Strategy Pattern

The Strategy Pattern is employed to calculate discounts based on different policies. Each discount policy (e.g., percentage-based, amount-based) is implemented as a separate class (PercentageDiscountStrategy, AmountDiscountStrategy). This allows flexibility and easy extensibility of the discount calculation mechanism.

Benefits of This Approach:

- Extensibility: New discount strategies can be added without modifying existing code. We can simply create a new class implementing the DiscountStrategy interface.
- Open/Closed Principle: The strategy pattern adheres to the Open/Closed Principle by enabling extension of functionality without altering existing code.
- Ease of Testing: Each strategy is an independent class, making it easy to test individually.

### Configuration via File

Discount policies are defined in a YAML configuration file (discount-policies.yml). This enables administrators or business owners to define which discounts to apply and under what conditions without changing the application code.

Benefits of This Approach:
- Flexibility: Changes to discount policies do not require redeployment of the application. They can be managed by simply updating the configuration file.
- DDD Compliance: Using configuration files separates business rules (discount policies) from application logic, aligning with Domain-Driven Design principles.
- DRY Principle: Policies are defined in a single place, reducing redundancy and making maintenance easier.

## Project Structure

- controller: Defines the REST API layer, handling HTTP requests and communicating with the service layer.
- service: Contains the business logic for managing products and calculating discounts.
- config: Includes configuration classes, such as DiscountPoliciesConfigProperties, which loads discount policies from the configuration file.
- mapper: Uses MapStruct for mapping objects between different layers (e.g., DTOs to Entities).
- domain: Represents the domain model (commodities, discount policies).
- repository: Provides access to the data layer (PostgreSQL database).
- exception: Contains the global exception handler, providing consistent error handling across the application.

## API Endpoints
- POST /api/commodities: Adds a new product.
- GET /api/commodities/{commodityId}: Retrieves product information by its ID.
- GET /api/commodities/{commodityId}/calculate-discount: Calculates the discount for the specified product and quantity.
- GET /api/commodities/{commodityId}/final-price: Calculates the final price of the product after applying the discount.

### Running the Application

Build the application:

```agsl
mvn clean install
```

Run the application:
```agsl
docker-compose up --build
```

Access the API documentation (Swagger) at: http://localhost:8080/swagger-ui.html.

## Testing

The project uses Testcontainers to run a real PostgreSQL database inside a Docker container for integration testing. This ensures that tests are reliable and not dependent on local database configurations.


## Architectural and Technological Choices

- Java 17: Utilizing the latest features of Java, including improved memory management, security, and performance. It also allows the use of records for a more concise and expressive codebase.
- Spring Boot: Chosen for its rapid development capabilities, comprehensive ecosystem, and strong support for building REST APIs.
- Lombok: Reduces the boilerplate in entity and DTO classes, making the codebase cleaner and more maintainable.
- Testcontainers: Facilitates running real databases during integration testing, ensuring that tests closely mimic the production environment.
- Strategy Pattern: Provides a flexible way to define and extend discount policies without modifying existing code, adhering to the Open/Closed Principle.
- File-based Configuration: Offers an easy way to manage business rules and discount policies without altering application logic or code, enhancing flexibility.

## Extensibility

This solution is designed with extensibility in mind:

- Adding New Discount Policies: Simply create a new class implementing the DiscountStrategy interface and register it in the strategy factory.
- Changing Configuration: Update the YAML configuration file to modify discount policies dynamically.


