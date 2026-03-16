# API

Generated from repository state on 2026-03-16T13:25:26.764Z.

## Backend API Surface

The current repository snapshot does not expose controller annotations in tracked backend source, so endpoint extraction is incomplete. The automation still records backend types and marks this as a documentation gap.

### Backend Types

- `MembershipIdResolver (class) in backend/src/main/java/com/coco/modules/project/application/members/MembershipIdResolver.java`
- `JwtService (class) in backend/src/main/java/com/coco/security/jwt/JwtService.java`

### Database Contracts

- `backend/src/main/resources/db/migration/V3__normalize_project_roles.sql`
- `backend/src/main/resources/db/migration/V4__normalice_task_status.sql`

## Frontend Route Contracts

- `auth`
- `Inicio`
- `Tareas`
- `Proyectos`
- `Ususrios`
- `**`

## API Documentation Policy

- When controller classes exist, extend the generator to parse Spring mapping annotations.
- When OpenAPI is available in CI, export `openapi.json` into `docs/generated/`.
- Any feature PR that adds routes or endpoints must run `npm run automation:sync`.
