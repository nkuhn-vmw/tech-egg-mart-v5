# Project Documentation

## Overview
Tech Egg Mart is an e‑commerce web application that allows users to browse electronic products, manage a shopping cart, and place orders. The application is built with a **Spring Boot** backend, **Thymeleaf** templates for server‑side rendering, and **HTMX** for progressive enhancement.

## Architecture

```
+-------------------+        +-------------------+        +-------------------+
|   Frontend (UI)   | <----> |   Spring Boot API | <----> |   H2 Database    |
|   Thymeleaf +    |        |   Controllers,   |        |   (in‑memory)    |
|   HTMX)           |        |   Services,      |        |   JPA Entities   |
+-------------------+        |   Security (JWT) |        +-------------------+
                             +-------------------+
```

- **Presentation Layer** — Thymeleaf templates rendered on the server, enhanced with HTMX for dynamic partial updates.
- **API Layer** — REST controllers expose CRUD operations for **Product**, **Category**, **Review**, **Cart**, and **Authentication**.
- **Service Layer** — Business logic, validation, and transaction handling.
- **Persistence Layer** — JPA entities (`Product`, `Category`, `Review`, `User`) with Spring Data repositories.
- **Security** — JWT‑based authentication, role‑based authorization (USER, ADMIN).
- **Database** — H2 in‑memory database configured via `application.yml` (easy for development and CI).

## API Endpoints

| Method | URL | Description | Secured Roles |
|--------|-----|-------------|---------------|
| `POST /auth/login` | Authenticate a user and receive a JWT. | Public |
| `GET /api/products` | List all products (supports optional `category`, `minPrice`, `maxPrice` query params). | USER, ADMIN |
| `GET /api/products/{id}` | Get product details including reviews. | USER, ADMIN |
| `POST /api/products` | Create a new product. | ADMIN |
| `PUT /api/products/{id}` | Update an existing product. | ADMIN |
| `DELETE /api/products/{id}` | Delete a product. | ADMIN |
| `GET /api/categories` | List all categories. | USER, ADMIN |
| `GET /api/categories/{slug}` | Get a single category with its products. | USER, ADMIN |
| `POST /api/categories` | Create a category. | ADMIN |
| `PUT /api/categories/{id}` | Update a category. | ADMIN |
| `DELETE /api/categories/{id}` | Delete a category. | ADMIN |
| `POST /api/products/{productId}/reviews` | Add a review to a product. | USER |
| `GET /api/products/{productId}/reviews` | List reviews for a product. | USER, ADMIN |
| `PUT /api/reviews/{id}` | Update a review (owner or ADMIN). | USER, ADMIN |
| `DELETE /api/reviews/{id}` | Delete a review (owner or ADMIN). | USER, ADMIN |
| `GET /api/cart` | Retrieve current session cart. | USER |
| `POST /api/cart/items` | Add an item to the cart. | USER |
| `PUT /api/cart/items/{itemId}` | Update quantity of a cart item. | USER |
| `DELETE /api/cart/items/{itemId}` | Remove an item from the cart. | USER |
| `POST /api/cart/checkout` | Perform a checkout (placeholder). | USER |

All request bodies are validated using Bean Validation (`@Valid`). Validation errors return **400 Bad Request** with a JSON payload describing the violations.

## Request / Response Examples

### Login Request
```json
POST /auth/login
{
  "username": "john",
  "password": "secret"
}
```
**Response** (200 OK)
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "john",
  "roles": ["ROLE_USER"]
}
```

### Create Product (ADMIN)
```json
POST /api/products
Authorization: Bearer <jwt>
Content-Type: application/json
{
  "name": "Wireless Mouse",
  "description": "Ergonomic wireless mouse",
  "price": 29.99,
  "imageUrl": "https://example.com/mouse.jpg",
  "categoryId": 3
}
```
**Response** (201 Created)
```json
{
  "id": 42,
  "name": "Wireless Mouse",
  "description": "Ergonomic wireless mouse",
  "price": 29.99,
  "imageUrl": "https://example.com/mouse.jpg",
  "category": {"id":3,"name":"Accessories"},
  "reviews": []
}
```

## Deployment Instructions

1. **Prerequisites**
   - JDK 21 (or compatible) installed.
   - Gradle wrapper (included).
   - Cloud Foundry CLI (`cf`) if deploying to CF, or Docker if containerising.

2. **Local Development**
   ```bash
   ./gradlew bootRun
   ```
   The app will start on `http://localhost:8080`. H2 console is available at `/h2-console` (default credentials `sa` / empty password).

3. **Build an Executable JAR**
   ```bash
   ./gradlew clean build
   java -jar build/libs/tech-egg-mart-0.0.1-SNAPSHOT.jar
   ```

4. **Deploy to Cloud Foundry**
   ```bash
   cf login -a https://api.<region>.cf.cloud.ibm.com
   cf push tech-egg-mart -p build/libs/tech-egg-mart-0.0.1-SNAPSHOT.jar -b java_buildpack
   ```
   The `manifest.yml` in the repo defines the memory, instance count, and health check settings.

5. **Docker (optional)**
   ```Dockerfile
   FROM eclipse-temurin:21-jdk-alpine
   VOLUME /tmp
   COPY build/libs/*.jar app.jar
   ENTRYPOINT ["java","-jar","/app.jar"]
   ```
   Build and run:
   ```bash
   docker build -t tech-egg-mart .
   docker run -p 8080:8080 tech-egg-mart
   ```

## Monitoring & Logging
- Spring Boot Actuator endpoints are exposed under `/actuator` (health, metrics, info). They are secured and require ADMIN role.
- Logs are written to console in a structured format; configure `logback-spring.xml` for file output if needed.

## Further Enhancements
- Replace the in‑memory H2 DB with PostgreSQL for production.
- Implement real payment gateway integration for checkout.
- Add pagination and sorting to product listings.
- Write OpenAPI (Swagger) documentation for the REST API.

---
*Documentation generated on $(date)*
