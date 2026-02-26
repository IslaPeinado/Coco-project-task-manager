# Arquitectura Frontend

## Contexto

Frontend en Angular 20 con standalone APIs y TypeScript estricto.

Objetivo: UI para gestion de proyectos/tareas conectada a API segura con JWT.

## Estructura real

```text
src/app
|- app.config.ts
|- app.routes.ts
|- core/
|- features/
|- layout/
`- shared/
```

## Contrato tecnico implementado

- `authInterceptor` global para `Authorization: Bearer <token>`.
- `TokenService` con `localStorage` (`access_token`).
- Carga lazy de features.
- Pruebas unitarias con Karma/Jasmine.

## Estado actual (as-is)

- Routing parcial.
- `authGuard` aun sin reglas finales.
- Parte de UI base todavia en placeholder.

## Mejoras recomendadas

1. Cerrar flujo de auth de punta a punta.
2. Aplicar guard real en rutas privadas.
3. Introducir capa API tipada por feature.
4. Consolidar layouts publico/privado.
5. Unificar manejo de errores HTTP.

## Diagramas

- `diagrams/frontend-architecture.puml`

## Referencias

- Global: `../architecture/overview.md`
- Design system: `../design/design-system/design-system.md`
- Runbook: `../operations/runbook.md`