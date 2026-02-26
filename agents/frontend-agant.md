## COCO Frontend Agent Contract (Machine-Oriented)

### 1) Project Identity
- repo_root: `Coco-project-task-manager`
- frontend_root: `frontend/` (intentional name, do not rename)
- framework: `Angular 20.x`
- style: `standalone APIs + strict TypeScript`
- primary_scope: `UI, routing, app state, frontend auth integration`

### 2) Runtime and Tooling Contract
- package manager: `npm`
- build system: `Angular CLI`
- test stack: `Karma + Jasmine`
- strict flags:
  - `strict: true`
  - `strictTemplates: true`
- global styles source: `frontend/src/styles.css`

### 3) Runbook
- install dependencies:
  - `cd frontend && npm install`
- start dev server:
  - `npm run start`
- build:
  - `npm run build`
- watch build:
  - `npm run watch`
- run tests:
  - `npm run test`

### 4) Canonical Structure
- entrypoint:
  - `src/main.ts`
- app configuration:
  - `src/app/app.config.ts`
- root routes:
  - `src/app/app.routes.ts`
- feature routes:
  - `src/app/features/*/*.routes.ts`
- cross-cutting:
  - `src/app/core` (guards, interceptors, services)
- shared reusable UI:
  - `src/app/shared/components`
- layout wrappers:
  - `src/app/layout`

### 5) Routing Contract
- keep lazy-loading at root with `loadChildren` when feature boundaries exist.
- route-array exports must be uppercase constants (example: `AUTH_ROUTES`).
- each new feature must define its own `feature-name.routes.ts` and be connected from root routes.

### 6) Component and File Naming Rules (Do Not Deviate)
- follow existing shared component naming pattern:
  - `name.ts`
  - `name.html`
  - `name.css`
  - `name.spec.ts`
- avoid introducing `*.component.ts` in mixed form unless explicit migration is requested.
- keep components standalone (no new `NgModule` unless explicitly requested).

### 7) State, Template, and DI Contract
- prefer signals for local reactive state:
  - `signal`, `computed`
- prefer new Angular control flow in templates:
  - `@if`, `@for`, `@switch`
- keep complex logic in TypeScript, not template expressions.
- prefer `inject()` where it improves readability and consistency.
- disallow `any` unless unavoidable and justified; prefer explicit models.

### 8) HTTP/Auth Contract
- HTTP client configuration source:
  - `src/app/app.config.ts`
- interceptor source:
  - `authInterceptor` in core interceptors
- token behavior:
  - append `Authorization: Bearer <token>` only if token exists and header is absent.
- token persistence:
  - `TokenService` + `localStorage` key `access_token`
- compatibility rule:
  - keep auth behavior backward-compatible unless change is explicitly requested.

### 9) UI Reality Constraints
- current root template may contain starter placeholder markup in `src/app/app.html`.
- do not assume final product UI from placeholder content.
- avoid broad visual rewrites unless required by task scope.

### 10) Testing Contract
- add/update `*.spec.ts` for:
  - services
  - interceptors
  - guards
  - non-trivial components
- for HTTP tests use:
  - `provideHttpClientTesting()`
  - `HttpTestingController`
- do not introduce Jest or alternative frameworks unless explicitly requested.

### 11) Change Protocol For Agents
1. verify real paths under `frontend/src/app`.
2. identify impacted feature/domain boundary.
3. implement minimal changes aligned with current conventions.
4. update or add tests proportional to behavior change.
5. run `npm run build` and relevant tests.
6. report files changed + behavior impact + validation result.

### 12) Safety Rules
- do not modify unrelated placeholders, globals, or styles.
- do not introduce new conventions silently.
- if a new convention is intentionally introduced, document it in this file in the same change set.
