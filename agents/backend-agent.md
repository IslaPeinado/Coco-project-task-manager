## COCO Backend Agent Contract (Machine-Oriented)

### 1) Project Identity
- repo_root: `coco-backend`
- backend_root: `backend/`
- language: `Java 21`
- framework: `Spring Boot 4.x`
- architecture_style: `layered + ports/adapters (hexagonal pragmatic)`
- base_package: `com.coco`
- active_modules:
  - `modules.user`
  - `modules.project`
  - `modules.task`

### 2) Canonical Package Layout
- `com.coco.modules.<module>.api` -> REST controllers + request/response DTOs + API mappers.
- `com.coco.modules.<module>.application` -> use cases (application services).
- `com.coco.modules.<module>.application.port` -> outbound interfaces (`*Port`).
- `com.coco.modules.<module>.domain` -> domain models and policies.
- `com.coco.modules.<module>.infra.persistence` -> JPA entities/repositories/adapters.
- `com.coco.modules.<module>.infra.security|mapper` -> technical adapters.
- cross-cutting:
  - `com.coco.security` (JWT, filter chain, auth handlers)
  - `com.coco.common.error` (error envelope + global handler)
  - `com.coco.config` (OpenAPI, CORS/Jackson config)

### 3) Request Flow Contract
1. HTTP request enters `SecurityFilterChain` (`JwtAuthFilter`).
2. Controller in `api` receives DTO.
3. Controller builds command/request object (`*Command` or request record values).
4. Controller calls `*UseCase.execute(...)` (or `register/login` naming in auth).
5. Use case validates business constraints + authorization.
6. Use case calls `*RepositoryPort` / other ports.
7. Adapter in `infra` resolves port using JPA repositories/entities.
8. Controller maps domain -> response DTO.
9. Errors bubble to `GlobalExceptionHandler` -> `ApiError`.

### 4) Naming Rules (Do Not Deviate)
- class suffixes:
  - use case: `*UseCase` (e.g., `CreateTaskUseCase`)
  - outbound port: `*Port` (e.g., `TaskRepositoryPort`)
  - infra adapter: `*Adapter` (e.g., `TaskRepositoryAdapter`)
  - jpa repository: `*JpaRepository`
  - controller: `*Controller`
  - domain model: plain noun (`Task`, `Project`, `User`)
- common variable names in code:
  - controller request body: `request` or `req`
  - command variable: `cmd`
  - use case dependencies: concise aliases (`taskRepo`, `projectRepo`, `statusRepo`, `authz`)
  - resource identifiers: `id`, `projectId`, `taskId`, `userId`, `roleId`
  - temporal values: `createdAt`, `updatedAt`, `archivedAt`, `cutoff`
- method names:
  - controller CRUD: `list`, `get`, `create`, `update`, `delete|archive`
  - application main method: `execute(...)` (except explicit auth methods like `login/register`)

### 5) Endpoint Inventory (Current)
- auth:
  - `POST /api/auth/register`
  - `POST /api/auth/login`
- projects:
  - `GET /api/projects`
  - `GET /api/projects/{id}`
  - `POST /api/projects`
  - `PUT /api/projects/{id}`
  - `DELETE /api/projects/{id}` (archive logical)
- project members:
  - `GET /api/projects/{projectId}/members`
  - `POST /api/projects/{projectId}/members`
  - `PUT /api/projects/{projectId}/members/{userId}`
  - `DELETE /api/projects/{projectId}/members/{userId}`
- tasks:
  - `GET /api/projects/{projectId}/tasks`
  - `GET /api/projects/{projectId}/tasks/{taskId}`
  - `POST /api/projects/{projectId}/tasks`
  - `PUT /api/projects/{projectId}/tasks/{taskId}`
  - `PUT /api/projects/{projectId}/tasks/{taskId}/status`
  - `DELETE /api/projects/{projectId}/tasks/{taskId}`

### 6) Security/Auth Contract
- default policy: all endpoints authenticated except:
  - `POST /api/auth/**`
  - `/swagger-ui/**`
  - `/v3/api-docs/**`
  - `/actuator/health`, `/actuator/info`
  - `OPTIONS /**`
- jwt properties prefix: `coco.jwt.*`
  - `coco.jwt.secret`
  - `coco.jwt.expiration-seconds`
  - `coco.jwt.issuer`
- auth token format in response: `Bearer` token (see `AuthResponse`).

### 7) Error Contract
- centralized in `GlobalExceptionHandler`.
- exception -> status mapping:
  - `NotFoundException` -> 404
  - `ConflictException` -> 409
  - `ForbiddenException` -> 403
  - `UnauthorizedException` -> 401
  - validation (`MethodArgumentNotValidException`, `ConstraintViolationException`) -> 400
  - fallback `Exception` -> 500
- response envelope type: `ApiError` with `code`, `message`, `path`, `timestamp`, `details[]`.

### 8) Persistence Contract
- DB engine in current backend: `PostgreSQL`.
- migrations path: `backend/src/main/resources/db/migration`.
- existing migrations:
  - `V1__.sql`
  - `V2__projects_archived_at.sql`
- primary tables:
  - `cocouser`
  - `project`
  - `tasks`
  - `task_status`
  - `role`
  - `cocouser_project_role`

### 9) Config/Profiles Contract
- base config: `backend/src/main/resources/application.properties`.
- profiles:
  - local: `application-local.properties`
  - test: `application-test.properties`
  - prod: `application-prod.properties`
- prod env vars expected:
  - `PORT`
  - `DB_URL`
  - `DB_USER`
  - `DB_PASSWORD`
  - `JWT_SECRET`
  - `JWT_EXP_SECONDS`
  - `JWT_ISSUER`
  - `CORS_ALLOWED_ORIGINS`

### 10) Test Contract
- command (from `backend/`): `./mvnw test` (Windows: `mvnw.cmd test`)
- stacks used:
  - JUnit 5
  - Mockito
  - Spring Boot Test
  - Spring Security Test
  - Testcontainers PostgreSQL
- integration base class: `AbstractIntegrationTest`.

### 11) Change Protocol For Agents
1. Identify target module (`user|project|task`).
2. Add/modify API DTO + controller method first.
3. Add/modify use case in `application`.
4. If persistence needed, update `*Port` and adapter implementation.
5. Update domain model only if business state changes.
6. Add Flyway migration for schema changes (never edit previous migration in place).
7. Add/adjust tests (`application` unit + controller/security integration when applicable).
8. Verify security exposure in `SecurityConfig`.
9. Keep naming and method signatures consistent with existing patterns.

### 12) Known Codebase Facts
- file `backend/src/main/java/com/coco/modules/user/api/UserController.java` currently exists but is empty (0 bytes).
- README mixes high-level statements, but runtime backend source of truth is under `backend/`.
