# API

Generated from repository state.

## Backend API Surface

The current repository snapshot does not expose controller annotations in tracked backend source, so endpoint extraction is incomplete. The automation still records backend types and marks this as a documentation gap.

### Backend Types

- None detected

### Database Contracts

- None detected

## Frontend Route Contracts

- None detected

## API Documentation Policy

- When controller classes exist, extend the generator to parse Spring mapping annotations.
- When OpenAPI is available in CI, export `openapi.json` into `docs/generated/`.
- Any feature PR that adds routes or endpoints must run `npm run automation:sync`.
