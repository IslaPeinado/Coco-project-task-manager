# Pruebas Frontend

## Objetivo

Documentar las pruebas frontend actualmente implementadas y verificables en este repositorio.

## Stack de testing esperado

- Unit testing: Karma + Jasmine.
- Ejecucion en CI: Node 20 (o version definida en `frontend/.nvmrc`).

## Flujo de validacion en CI

La pipeline frontend definida en:

- `.github/workflows/ci-frontend.yml`
- `.github/actions/action.yml`

ejecuta estos pasos:

1. `npm ci`
2. `npm run lint --if-present`
3. `npm test -- --watch=false`
4. `npm run build`

## Estado actual en este repo/rama

- La carpeta `frontend/` no esta presente en el workspace actual.
- Por ese motivo, no se pueden listar casos `.spec.ts` concretos desde esta rama.
- La validacion de pruebas frontend queda definida a nivel de CI y de documentacion, pendiente de inspeccionar el modulo `frontend` cuando este disponible.

## Criterio para actualizar este documento

Cuando `frontend/` este disponible, ampliar con:

1. Inventario de suites (`src/**/*.spec.ts`).
2. Cobertura por modulo (auth, proyectos, tareas, componentes compartidos).
3. Casos criticos cubiertos (guards, interceptores, servicios API, componentes).
