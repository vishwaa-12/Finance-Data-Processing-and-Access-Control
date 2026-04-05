# Finance Dashboard Backend API

This is a Java Spring Boot backend built to manage financial records and user access control for a Finance Dashboard.

## Core Features Implemented

1. **User and Role Management**:
   - `User` entity with `id`, `username`, `password`, `role`, and `active` fields.
   - Roles: `ADMIN`, `ANALYST`, `VIEWER`.
   - Admin APIs to Create, Update, Delete users.

2. **Financial Records Management**:
   - `FinancialRecord` entity tracking `amount`, `type` (`INCOME`/`EXPENSE`), `category`, `date`, `notes`.
   - Filtering capabilities (by `category`, `type`, and `date` range) included in the endpoints.
   - Includes full CRUD operations governed by role access.

3. **Dashboard Summary Logic**:
   - Endpoint `/api/dashboard/summary` providing aggregated insights:
     - `totalIncome`, `totalExpenses`, `netBalance`.
     - `incomeByCategory` and `expensesByCategory` maps showing where funds are allocated.
     - `recentActivity` showing the latest 5 records.

4. **Access Control (Authorization)**:
   - Built with **Spring Security** leveraging stateless JWT Authentication.
   - Using `JwtAuthenticationFilter` with Token generation based on `jjwt`.
   - Role-Based Access Control via Global Method Security (`@PreAuthorize`):
     - `VIEWER`: Can view records.
     - `ANALYST`: Can view records and aggregated summaries.
     - `ADMIN`: Has complete management rights for users and full CRUD operations on records.
   - Configured CORS headers globally.

5. **Validation, Auditing, and Error Handling**:
   - Integrated `@ControllerAdvice` for global exception formatting (returns clean JSON structures).
   - Validation enabled on request payloads using `@Valid` for inputs.
   - Leveraging JPA Auditing with `@CreatedDate` & `@LastModifiedDate` for full lifecycle integrity.

6. **Data Persistence**:
   - Contains dynamic DB connection settings with an `application-prod.properties` mapped for **PostgreSQL**.
   - Defaults to **H2 In-Memory Database** during development runs.
   - Paging integration natively handled with `Pageable` on heavy list requests.

## Extra Enhancements
- Configured **Swagger UI / OpenAPI 3** at `/swagger-ui/index.html` via `springdoc-openapi` for endpoint testing and API documentation.

## Running Locally

To run the application:
```sh
mvn spring-boot:run
```

Once running:
- **API Base URL**: `http://localhost:8080/api`
- **Authentication**: Obtain tokens through `POST /api/auth/login`. Request headers should use `Authorization: Bearer <TOKEN>`.
- **H2 Database Console**: `http://localhost:8080/h2-console`
- **Swagger Documentation**: `http://localhost:8080/swagger-ui/index.html`

### Default Credentials
- **Admin**: `admin` / `admin123`
- **Analyst**: `analyst` / `analyst123`
- **Viewer**: `viewer` / `viewer123`
