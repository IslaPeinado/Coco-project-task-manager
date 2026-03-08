# Pruebas Backend

## Objetivo

Documentar las pruebas backend implementadas actualmente en el proyecto.

## Stack de testing

- JUnit 5
- Mockito
- Spring Boot Test
- Spring Web MVC Test (`@WebMvcTest`)
- Spring Security Test
- Testcontainers (PostgreSQL)

## Tipos de pruebas implementadas

### 1. Unitarias (casos de uso, dominio, servicios)

- `backend/src/test/java/com/coco/modules/user/application/auth/LoginUseCaseTest.java`
- `backend/src/test/java/com/coco/modules/user/application/auth/RegisterUseCaseTest.java`
- `backend/src/test/java/com/coco/modules/user/application/user/ChangePasswordUseCaseTest.java`
- `backend/src/test/java/com/coco/modules/user/application/user/GetMeUseCaseTest.java`
- `backend/src/test/java/com/coco/modules/user/application/user/GetUserUseCaseTest.java`
- `backend/src/test/java/com/coco/modules/user/application/user/UpdateUserUseCaseTest.java`
- `backend/src/test/java/com/coco/modules/project/application/ArchiveProjectUseCaseTest.java`
- `backend/src/test/java/com/coco/modules/project/application/CreateProjectUseCaseTest.java`
- `backend/src/test/java/com/coco/modules/project/application/UpdateProjectUseCaseTest.java`
- `backend/src/test/java/com/coco/modules/project/domain/ProjectPolicyTest.java`
- `backend/src/test/java/com/coco/modules/task/application/ChangeTaskStatusUseCaseTest.java`
- `backend/src/test/java/com/coco/modules/task/application/CreateTaskUseCaseTest.java`
- `backend/src/test/java/com/coco/security/jwt/JwtServiceTest.java`
- `backend/src/test/java/com/coco/modules/user/infra/security/LegacyPasswordMigrationRunnerTest.java`

### 2. Integracion web/seguridad (controladores con `@WebMvcTest`)

- `backend/src/test/java/com/coco/modules/user/api/AuthControllerSecurityIntegrationTest.java`
- `backend/src/test/java/com/coco/modules/user/api/UserControllerSecurityIntegrationTest.java`
- `backend/src/test/java/com/coco/modules/project/api/ProjectControllerSecurityIntegrationTest.java`
- `backend/src/test/java/com/coco/modules/project/api/ProjectMembersControllerSecurityIntegrationTest.java`
- `backend/src/test/java/com/coco/modules/task/api/TaskControllerSecurityIntegrationTest.java`

Cobertura principal de estas suites:
- autenticacion requerida en endpoints privados (`401`)
- acceso permitido con usuario autenticado (`200/201/204`)
- mapeo de errores de negocio/validacion (`400/403/404/409`)

### 3. Integracion con base de datos real (Testcontainers)

- `backend/src/test/java/com/coco/AbstractIntegrationTest.java`

Esta clase base arranca PostgreSQL en contenedor para pruebas de integracion que la extiendan.

## Ejecucion

Desde `backend/`:

1. Suite completa:
   - `./mvnw test`
2. Suite concreta:
   - `./mvnw -Dtest=NombreDeClaseTest test`
3. Varias suites:
   - `./mvnw -Dtest="ClaseATest,ClaseBTest" test`

## Cobertura pendiente identificada

- Casos avanzados de tareas de asignacion/movimiento/desasignacion, una vez implementados los casos de uso correspondientes.
