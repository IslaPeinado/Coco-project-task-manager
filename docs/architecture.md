# Architecture

Generated from repository state.

## System Overview

COCO is currently a split-stack project with:

- Angular frontend under `frontend/`
- Java backend source under `backend/src/`
- Docs-as-code assets under `docs/`
- Agent guidance under `agents/`

## Containers

### Frontend

- Framework: Angular 20
- Entry routes: `auth`, `Inicio`, `Tareas`, `Proyectos`, `Ususrios`, `**`
- Source files tracked: 41
- Test files tracked: 22

### Backend

- Java source files tracked: 2
- Java test files tracked: 5
- Public backend types: `MembershipIdResolver`, `JwtService`
- SQL migrations: 2

### Delivery and Automation

- GitHub Actions workflows: `ci-frontend.yml`, `ci-backend.yml`, `docs-ai-platform.yml`
- Generated AI context: `ai-context/`
- Generated retrieval index: `ai-context/rag-index.json`

## Constraints

- Backend source and tests exist, but build tooling (`pom.xml`, `mvnw`, `build.gradle`, `gradlew`) is not present in the repository snapshot. CI must treat backend execution as conditional until those files are restored.
- Frontend has executable package metadata under `frontend/package.json`.
- Existing documentation is substantial but fragmented across domain folders; the generated docs in `docs/*.md` provide a stable AI-friendly entrypoint.
