# Arquitectura Backend (backend/src)

## 1. Resumen

El backend sigue una arquitectura modular con enfoque hexagonal pragmatica:

- `api`: controladores HTTP y DTOs.
- `application`: casos de uso y orquestacion.
- `domain`: entidades y reglas de negocio.
- `infra`: adaptadores tecnicos (JPA, seguridad, mappers).

La base de paquetes real es `com.coco` y los modulos funcionales actuales son:

- `modules.user`
- `modules.project`
- `modules.task`

## 2. Flujo principal

```text
HTTP Request
  -> Security Filter Chain (JWT)
    -> Controller (api)
      -> Use Case (application)
        -> Port (application/port)
          -> Adapter (infra)
            -> JPA Repository
              -> PostgreSQL
```

Errores, seguridad y configuracion actuan como capas transversales.

## 3. Diagrama de arquitectura

```
  A[HTTP Request] --> B[SecurityConfig + JwtAuthFilter]
  B --> C[API Controllers]
  C --> D[Application UseCases]
  D --> E[Domain Entities and Rules]
  D --> F[Ports]
  F --> G[Infra Adapters]
  G --> H[(PostgreSQL)]
  H -. schema/versioning .-> I[Flyway migrations]
  D -. mapped errors .-> J[GlobalExceptionHandler]
```

## 4. Estructura de paquetes

```
com.coco
├── CocoApplication
├── common
│   ├── error
│   ├── pagination
│   └── util
├── config
├── security
│   ├── jwt
│   └── user
└── modules
    ├── user
    │   ├── api
    │   ├── application
    │   │   ├── auth
    │   │   ├── user
    │   │   └── port
    │   ├── domain
    │   └── infra
    │       ├── persistence
    │       ├── security
    │       └── mapper
    ├── project
    │   ├── api
    │   ├── application
    │   │   ├── members
    │   │   └── port
    │   ├── domain
    │   └── infra
    │       ├── persistence
    │       └── security
    └── task
        ├── api
        ├── application
        │   └── port
        ├── domain
        └── infra
            ├── persistence
            └── mapper
```

## 5. Modulos y responsabilidades

### 5.1 `user`

- Autenticacion (`/api/auth`) y emision de tokens JWT.
- Gestion de usuario autenticado y operaciones de perfil/consulta.
- Puertos clave: `UserRepositoryPort`, `PasswordEncoderPort`, `TokenServicePort`.

### 5.2 `project`

- CRUD y archivado de proyectos.
- Gestion de miembros y roles por proyecto.
- Autorizacion por permisos de proyecto via `ProjectAuthorizationService`.

### 5.3 `task`

- CRUD de tareas por proyecto.
- Asignacion/desasignacion, cambio de estado y movimiento.
- Validacion de estado via catalogo de estados (`TaskStatusRepositoryPort`).

## 6. Componentes transversales

- Seguridad:
  - `SecurityConfig`, `JwtAuthFilter`, `RestAuthenticationEntryPoint`, `RestAccessDeniedHandler`.
  - Politica stateless y endpoints protegidos por defecto.
- Manejo de errores:
  - `GlobalExceptionHandler` centraliza respuestas de error (`ApiError`).
- Persistencia:
  - Spring Data JPA + adaptadores por modulo.
  - Flyway en `src/main/resources/db/migration`.
- Configuracion:
  - `application.properties` + perfiles `application-local.properties`, `application-test.properties`, `application-prod.properties`.


