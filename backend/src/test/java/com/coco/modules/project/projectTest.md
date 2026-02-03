# Tests del módulo Project

Este documento resume **cómo diseñé y por qué implementé** los tests del módulo `project` en este backend.
La idea fue cubrir reglas de negocio críticas, seguridad y consistencia de la API, con foco en calidad de código real de producto.

## Objetivo

Asegurar que el módulo de proyectos:

- aplique correctamente las reglas de negocio (crear, actualizar, archivar)
- respete permisos por rol
- y responda de forma segura en endpoints HTTP protegidos

## Enfoque de testing

Separé la estrategia en dos niveles:

1. **Tests unitarios con Mockito** para validar lógica pura de casos de uso.
2. **Tests de integración con MockMvc + Spring Security Test** para validar comportamiento real de endpoints y autorización.

Esto me permite detectar rápido errores de dominio y, además, verificar que todo funciona integrado con Spring.

## Tests unitarios implementados

### 1) `CreateProjectUseCaseTest`

Valida que al crear un proyecto:

- se persistan correctamente `name`, `description`, `logoUrl`,
- el estado inicial sea `ACTIVE`,
- y se cree membership OWNER para el usuario autenticado.

**Por qué es importante:** evita proyectos huérfanos sin ownership y asegura consistencia desde el primer guardado.

### 2) `ArchiveProjectUseCaseTest`

Valida dos escenarios:

- usuario OWNER puede archivar,
- usuario no OWNER recibe `ForbiddenException` y no se archiva.

**Por qué es importante:** el archivado cambia ciclo de vida del proyecto y debe estar fuertemente protegido por permisos.

### 3) `UpdateProjectUseCaseTest`

Valida dos escenarios:

- OWNER puede editar y persistir cambios,
- rol sin permisos recibe `ForbiddenException` y no se persiste nada.

**Por qué es importante:** protege la integridad funcional del proyecto y evita cambios no autorizados.

## Tests de integración implementados

### `ProjectControllerSecurityIntegrationTest`

Con `MockMvc` y `Spring Security Test` validé:

- acceso sin autenticación a endpoints protegidos (`/api/projects`) -> rechazado (`403` en la configuración actual),
- acceso autenticado con `@WithMockUser` -> permitido,
- respuesta correcta del endpoint de creación (payload esperado).

También mockeé el `JwtAuthFilter` en modo pass-through para aislar el test en **autorización HTTP del controller** y no en parsing JWT.

## Decisiones técnicas relevantes

- Usé **unit tests** para lógica de negocio porque son rápidos, claros y mantenibles.
- Usé **integration tests** para seguridad web porque los problemas reales suelen aparecer en la cadena HTTP/filtros/configuración.
- Priorizé escenarios de mayor riesgo: permisos por rol, archivado y creación con ownership.

## Cómo ejecutar

Desde `backend/`:

```bash
./mvnw test
```
