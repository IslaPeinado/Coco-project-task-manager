# Arquitectura Backend

## Contexto

Backend en Java 21 + Spring Boot, con modulos de negocio:
- `user`
- `project`
- `task`

Base de paquetes: `com.coco.modules`.

## Estructura por capas

```text
api          -> controllers + DTOs
application  -> casos de uso + puertos
domain       -> reglas de negocio
infra        -> adaptadores, persistencia, seguridad
```

Cross-cutting:
- `com.coco.security`
- `com.coco.common.error`
- `com.coco.config`

## Flujo de peticion

```text
HTTP -> SecurityFilterChain(JWT) -> Controller
      -> UseCase -> Port -> Adapter -> JPA -> PostgreSQL
```

Errores centralizados en `GlobalExceptionHandler`.

## Contratos clave

- Seguridad:
  - JWT requerido salvo endpoints publicos.
  - Politica stateless.
- Errores:
  - envelope uniforme (`ApiError`).
- Persistencia:
  - migraciones Flyway.

## Mejoras recomendadas

1. Versionado de endpoints.
2. Telemetria con `traceId`.
3. Pruebas de contrato API.
4. Auditoria de eventos sensibles.

## Referencias

- Global: `../architecture/overview.md`
- Seguridad: `../security/security-architecture.md`
- Datos: `../database/schema.md`
- Testing backend: `testing.md`
