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

## Pruebas frontend (estado documentado)

- Framework de unit testing: Karma + Jasmine.
- Pipeline CI frontend: `npm ci`, `npm run lint --if-present`, `npm test -- --watch=false`, `npm run build`.
- Definicion CI: `.github/workflows/ci-frontend.yml` + `.github/actions/action.yml`.
- Estado en este repositorio/rama: no existe la carpeta `frontend/`, por lo que no hay suites de pruebas frontend inspeccionables aqui.
- Si se trabaja desde una rama o repositorio que si incluye `frontend/`, las pruebas activas deben revisarse en `frontend/src/**/*.spec.ts`.

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
- Testing frontend: `testing.md`
