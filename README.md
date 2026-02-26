# COCO

COCO es una aplicacion web fullstack para gestion de proyectos y tareas, construida como portfolio tecnico con enfoque profesional en arquitectura, seguridad y mantenibilidad.

## Stack principal

### Frontend
- Angular 20
- TypeScript
- Angular Router
- Formularios reactivos
- Interceptor JWT

### Backend
- Java 21
- Spring Boot
- Spring Security (JWT stateless)
- Spring Data JPA + Hibernate
- Flyway
- OpenAPI / Swagger

### Base de datos
- PostgreSQL

## Funcionalidades principales

### Autenticacion y seguridad
- Autenticacion con JWT.
- Separacion entre endpoints publicos y privados.
- Control de acceso por rol y contexto de proyecto.

### Proyectos
- CRUD de proyectos.
- Gestion de miembros y roles.
- Archivado logico de proyectos.

### Tareas
- CRUD de tareas por proyecto.
- Gestion de estado y asignacion.
- Reglas de validacion de negocio.

## Arquitectura

COCO sigue una arquitectura cliente-servidor en tres capas:

1. Presentacion: Angular (UI, navegacion, consumo de API).
2. Servicios: Spring Boot (casos de uso, seguridad, API REST).
3. Datos: PostgreSQL (persistencia relacional + migraciones Flyway).

![diagrama global](docs/architecture/diagrams/global-application-architecture.png)

## Calidad y buenas practicas

- Separacion por capas (`api`, `application`, `domain`, `infra`).
- Manejo global de errores.
- Migraciones versionadas con Flyway.
- Pruebas unitarias e integracion.
- CI con GitHub Actions.

## CI implementado

- Backend: `.github/workflows/ci-backend.yml`
- Frontend: `.github/workflows/ci-frontend.yml`

## Politica de ramas

- `main`:
  - Rama virgen de referencia del portfolio.
  - No se usa como rama de trabajo diario.
- `dev`:
  - Rama base activa para integracion.
  - Aqui se integran cambios de frontend y backend.
- `frontend`:
  - Rama de trabajo del area frontend.
  - Se divide en subramas por issue.
- `backend`:
  - Rama de trabajo del area backend.
  - Se divide en subramas por issue.

## Testing incluido

Backend:
- JUnit 5
- Mockito
- Spring Boot Test
- Spring Security Test
- Testcontainers (PostgreSQL)

Comando base de pruebas backend (desde `backend/`):

- `./mvnw test`

## Documentacion tecnica

- Indice general: `docs/README.md`
- Arquitectura global: `docs/architecture/overview.md`
- Roadmap de arquitectura: `docs/architecture/improvement-roadmap.md`
- Backend: `docs/backend/architecture.md`
- Frontend: `docs/frontend/architecture.md`
- Seguridad: `docs/security/security-architecture.md`
- Base de datos: `docs/database/schema.md`
- Operacion: `docs/operations/runbook.md`
- Diseno:
  - `docs/design/design-system/design-system.md`
  - `docs/design/mockups/light/`
  - `docs/design/mockups/dark/`
  - `docs/design/assets/Coco_Logo.svg`

## Ejecucion local

### Backend
1. Configurar variables de entorno (`DB_URL`, `DB_USER`, `DB_PASSWORD`, `JWT_SECRET`).
2. Ejecutar desde `backend/`:
   - `./mvnw spring-boot:run`

### Frontend
1. Instalar dependencias:
   - `cd frontend && npm install`
2. Ejecutar:
   - `npm run start`

## OpenAPI / Swagger

Con el backend levantado:
- Swagger UI: `http://localhost:8080/swagger-ui`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`