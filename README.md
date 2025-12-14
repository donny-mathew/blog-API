# Blog API System

A robust RESTful API for a Blog application built with **Spring Boot 3** and **Java 21**. This system features JWT-based authentication, role-based security, and a full suite of content management features.

## 🚀 Technologies Used
*   **Java 21** (LTS)
*   **Spring Boot 3.3.0**
*   **Spring Security 6** (with JWT Authentication)
*   **Spring Data JPA** (Hibernate)
*   **H2 Database** (In-memory, for easy testing)
*   **SpringDoc OpenAPI** (Swagger 3 Documentation)
*   **Maven** (Build Tool)
*   **Lombok** (Boilerplate reduction)

## ✨ Key Features
*   **Authentication & Security**:
    *   User Registration and Login (`/api/auth/**`).
    *   JWT (JSON Web Token) generation and validation.
    *   Role-Based Access Control (Admin vs User privileges).
*   **Post Management**:
    *   Create, Read, Update, Delete (CRUD) blog posts.
    *   Pagination and Sorting support for listing posts.
*   **Comment Management**:
    *   Add comments to specific posts.
    *   CRUD operations for comments.
    *   Relationship integrity checking (Comment belongs to Post).
*   **Quality of Life**:
    *   **Global Exception Handling**: Unified error responses.
    *   **Input Validation**: `@Valid` annotations for request bodies.
    *   **API Documentation**: Integrated Swagger UI.

## 🛠️ Setup & Installation
1.  **Prerequisites**: Ensure you have **Java 21** and **Maven** installed.
2.  **Clone the repository**:
    ```bash
    git clone https://github.com/donny-mathew/blog-API.git
    cd blog-API
    ```
3.  **Build the project**:
    ```bash
    mvn clean install
    ```
4.  **Run the application**:
    ```bash
    mvn spring-boot:run
    ```

## 🔌 API Documentation
Once the application is running, you can explore and test the APIs using the interactive Swagger UI:
👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

## 🗄️ Database Configuration
The project is currently configured to use an **in-memory H2 database**.
*   **Access Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
*   **JDBC URL**: `jdbc:h2:mem:blog`
*   **User**: `sa`
*   **Password**: `password`

## 🏗️ Project Structure
*   `controller`: REST endpoints for Auth, Posts, and Comments.
*   `service`: Business logic interfaces and implementations.
*   `repository`: Spring Data JPA repositories.
*   `entity`: JPA entities (`User`, `Role`, `Post`, `Comment`).
*   `payload`: DTOs (Data Transfer Objects).
*   `security`: JWT filter, Token provider, and UserDetails services.
*   `exception`: Custom exceptions and `ExceptionAdvice` global handler.
*   `configuration`: Security and OpenAPI configs.

## 📝 Configuration
Check `src/main/resources/application.properties` for configuration details specific to:
*   Database connection (H2)
*   JWT Secret and Expiration
*   Logging levels
