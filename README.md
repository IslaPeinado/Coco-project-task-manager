# COCO 🥥

COCO es una aplicación web orientada a entornos empresariales para la gestión de proyectos y tareas, diseñada con un enfoque de seguridad, mantenibilidad y escalabilidad. El frontend, construido con Angular 20, ofrece una experiencia fluida basada en formularios reactivos y navegación estructurada. El backend, desarrollado en Java 21 con Spring Boot, expone una API REST documentada con OpenAPI/Swagger, con endpoints protegidos mediante JWT y control de acceso por roles (USUARIO/ADMIN).
La persistencia se realiza en PostgreSQL Database utilizando JPA/Hibernate, cuidando el modelado relacional y las buenas prácticas de acceso a datos. El repositorio incluye documentación técnica y configuración de calidad (CI, plantillas, políticas de ramas) para reflejar un flujo de trabajo similar al de un equipo real.

---

## 🚀 Tecnologías utilizadas

### Frontend
- Angular
- TypeScript
- Angular Router
- Formularios reactivos
- Autenticación JWT (interceptor)

### Backend
- Java 21
- Spring Boot
- Spring Security (JWT stateless)
- Spring Data JPA + Hibernate
- Flyway
- OpenAPI / Swagger

### Base de datos
- PostgreSQL

## Funcionalidades Principales

### Autenticacion y seguridad
- Sistema de autenticacion para acceso seguro a la plataforma.
- Emision y validacion de tokens JWT para proteger operaciones privadas.
- Separacion entre accesos publicos y accesos autenticados.

### Permisos de negocio
- Modelo de autorizacion basado en roles dentro de cada proyecto.
- Definicion de permisos por nivel de responsabilidad.
- Control de acciones segun contexto de usuario y recurso.

### Proyectos
- Gestion del ciclo de vida de proyectos.
- Administracion de miembros y sus roles.
- Soporte para archivado logico como estrategia de preservacion de datos.

### Tareas
- Gestion de tareas asociadas a proyectos.
- Seguimiento del avance mediante estados de trabajo.
- Reglas de validacion para mantener consistencia funcional.

## Arquitectura

COCO sigue una arquitectura cliente-servidor en tres capas:

1. Presentacion: Frontend en Angular (UI, navegacion, consumo de API).
2. Servicios: Backend en Spring Boot (reglas de negocio, seguridad JWT, endpoints REST).
3. Datos: PostgreSQL (persistencia relacional y versionado de esquema con Flyway).

![diagrama global](docs\image.png)


## Calidad y buenas practicas

- DTOs y separacion por capas (`api`, `application`, `domain`, `infra`).
- Manejo global de errores con respuesta estandar.
- Migraciones de base de datos versionadas con Flyway.
- Pruebas unitarias e integracion en backend.
- Integracion continua con GitHub Actions.

## CI implementado

El repositorio incluye pipelines de CI en GitHub Actions:

- Backend (Spring Boot): `.github/workflows/ci-backend.yml`
  - Compila y ejecuta tests con Java 21.
  - Levanta PostgreSQL en servicio para validaciones de integracion.
- Frontend (Angular): `.github/workflows/ci-frontend.yml`
  - Instala dependencias, ejecuta lint, tests unitarios y build.

## Politica de ramas

Flujo del proyecto:

- `main`:
  - Rama estable de referencia.
  - Integra cambios desde `dev`.
  - Contempla ramas `pre` y `prod` para flujo profesional, aunque en este portfolio actualmente no se usan.
- `dev`:
  - Rama de integracion activa para desarrollo.
  - Desde aqui se derivan las ramas funcionales de frontend y backend.
- `frontend`:
  - Rama de trabajo para el area frontend.
  - Se divide en subramas por issue (ejemplo: `feat/frontend-123-login`).
- `backend`:
  - Rama de trabajo para el area backend.
  - Se divide en subramas por issue (ejemplo: `feat/backend-456-project-archive`).

Resumen visual:

- `main -> dev` (y referencia a `pre/prod` no activas en este portfolio)
- `dev -> frontend -> subramas por issue`
- `dev -> backend -> subramas por issue`

## Testing incluido

El backend incluye una base de pruebas automatizadas con foco en reglas de negocio y seguridad:

- `JUnit 5 (Jupiter)` como framework base de testing.
- `Mockito` para mocks en pruebas unitarias de casos de uso.
- `Spring Boot Test` para pruebas de contexto y soporte de integracion.
- `@WebMvcTest` + `MockMvc` para pruebas de controladores HTTP.
- `Spring Security Test` para escenarios de autenticacion/autorizacion.
- `Testcontainers (PostgreSQL)` para pruebas de integracion contra DB real.
- `H2` para escenarios de test ligeros en memoria.

Ejecucion de pruebas backend (desde `backend/`):

- `./mvnw test`

## Documentacion tecnica

Para detalle tecnico real y actualizado:

- Backend: `docs/model/architecture.md`
- Frontend (diseno y UX):
  - `docs/design/design-system/design-system.md`
  - `docs/design/mockups/light/`
  - `docs/design/mockups/dark/`
  - `docs/design/assets/Coco_Logo.svg`
- Base de datos: `docs/dataBase/dataBase.md`
- Diagramas:
  - `docs/global-application-architecture.puml`
  - `docs/model/plantuml/`
  - `docs/dataBase/database-schema.puml`

## Ejecucion local

### Backend
1. Configurar variables de entorno (`DB_URL`, `DB_USER`, `DB_PASSWORD`, `JWT_SECRET`).
2. Ejecutar desde `backend/`:
   - `./mvnw spring-boot:run`
   - o `mvn spring-boot:run`

### Frontend
1. Instalar dependencias:
   - `npm install`
2. Ejecutar:
   - `ng serve -o`

## OpenAPI / Swagger

Con el backend levantado:
- Swagger UI: `http://localhost:8080/swagger-ui`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
