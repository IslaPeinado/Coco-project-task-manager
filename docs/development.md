# Development

Generated from repository state on 2026-03-16T13:25:26.764Z.

## Repository Layout

- `frontend/`: Angular application
- `backend/`: Java source, tests and migrations
- `docs/`: versioned documentation
- `ai-context/`: AI-optimized knowledge base
- `adr/`: architecture decision records
- `prompts/`: reusable prompts for AI-assisted delivery
- `scripts/`: docs, validation and RAG automation

## Local Commands

- `cd frontend && npm ci`
- `cd frontend && npm test -- --watch=false --browsers=ChromeHeadless`
- `npm run automation:sync`
- `node scripts/query-rag.mjs "How is auth implemented?"`

## Current Gaps

- Backend source and tests exist, but build tooling (`pom.xml`, `mvnw`, `build.gradle`, `gradlew`) is not present in the repository snapshot. CI must treat backend execution as conditional until those files are restored.
- Root automation is Node-based and dependency-free to keep CI simple.
