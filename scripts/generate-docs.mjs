import { collectProjectFacts, writeFile, listToBullet } from './lib/project-analysis.mjs';

const facts = collectProjectFacts();

const backendBuildNote = facts.stack.backendBuildPresent
  ? 'Backend build tooling is present and can be executed directly in CI.'
  : 'Backend source and tests exist, but build tooling (`pom.xml`, `mvnw`, `build.gradle`, `gradlew`) is not present in the repository snapshot. CI must treat backend execution as conditional until those files are restored.';

function buildArchitectureDoc() {
  return `# Architecture

Generated from repository state on ${facts.generatedAt}.

## System Overview

COCO is currently a split-stack project with:

- Angular frontend under \`frontend/\`
- Java backend source under \`backend/src/\`
- Docs-as-code assets under \`docs/\`
- Agent guidance under \`agents/\`

## Containers

### Frontend

- Framework: Angular 20
- Entry routes: ${facts.frontend.routes.map((route) => `\`${route}\``).join(', ') || 'none detected'}
- Source files tracked: ${facts.frontend.source.length}
- Test files tracked: ${facts.frontend.specs.length}

### Backend

- Java source files tracked: ${facts.backend.java.length}
- Java test files tracked: ${facts.backend.tests.length}
- Public backend types: ${facts.backend.types.map((type) => `\`${type.name}\``).join(', ') || 'none detected'}
- SQL migrations: ${facts.backend.migrations.length}

### Delivery and Automation

- GitHub Actions workflows: \`ci-frontend.yml\`, \`ci-backend.yml\`, \`docs-ai-platform.yml\`
- Generated AI context: \`ai-context/\`
- Generated retrieval index: \`ai-context/rag-index.json\`

## Constraints

- ${backendBuildNote}
- Frontend has executable package metadata under \`frontend/package.json\`.
- Existing documentation is substantial but fragmented across domain folders; the generated docs in \`docs/*.md\` provide a stable AI-friendly entrypoint.
`;
}

function buildApiDoc() {
  return `# API

Generated from repository state on ${facts.generatedAt}.

## Backend API Surface

The current repository snapshot does not expose controller annotations in tracked backend source, so endpoint extraction is incomplete. The automation still records backend types and marks this as a documentation gap.

### Backend Types

${listToBullet(facts.backend.types.map((type) => `${type.name} (${type.kind}) in ${type.file}`))}

### Database Contracts

${listToBullet(facts.backend.migrations)}

## Frontend Route Contracts

${listToBullet(facts.frontend.routes)}

## API Documentation Policy

- When controller classes exist, extend the generator to parse Spring mapping annotations.
- When OpenAPI is available in CI, export \`openapi.json\` into \`docs/generated/\`.
- Any feature PR that adds routes or endpoints must run \`npm run automation:sync\`.
`;
}

function buildWorkflowsDoc() {
  return `# Workflows

Generated from repository state on ${facts.generatedAt}.

## Feature Workflow

1. Capture requirement in issue or ADR.
2. Generate or update unit and integration tests first.
3. Implement the smallest code change that passes tests.
4. Run frontend or backend validation.
5. Regenerate docs and AI context.
6. Commit code and generated artifacts together.

## API Workflow

1. Define contract in \`docs/api.md\` or OpenAPI.
2. Add contract or security tests.
3. Implement endpoint and persistence changes.
4. Update migrations if required.
5. Regenerate architecture and AI docs.

## Debug Workflow

1. Reproduce failure with a deterministic test.
2. Capture root cause in \`docs/troubleshooting.md\` or a regression test.
3. Patch minimally.
4. Re-run validation and regenerate artifacts.
`;
}

function buildDevelopmentDoc() {
  return `# Development

Generated from repository state on ${facts.generatedAt}.

## Repository Layout

- \`frontend/\`: Angular application
- \`backend/\`: Java source, tests and migrations
- \`docs/\`: versioned documentation
- \`ai-context/\`: AI-optimized knowledge base
- \`adr/\`: architecture decision records
- \`prompts/\`: reusable prompts for AI-assisted delivery
- \`scripts/\`: docs, validation and RAG automation

## Local Commands

- \`cd frontend && npm ci\`
- \`cd frontend && npm test -- --watch=false --browsers=ChromeHeadless\`
- \`npm run automation:sync\`
- \`node scripts/query-rag.mjs "How is auth implemented?"\`

## Current Gaps

- ${backendBuildNote}
- Root automation is Node-based and dependency-free to keep CI simple.
`;
}

function buildTestingDoc() {
  return `# Testing

Generated from repository state on ${facts.generatedAt}.

## Current Coverage Signals

- Frontend spec files: ${facts.frontend.specs.length}
- Backend test files: ${facts.backend.tests.length}

## AI-TDD Cycle

1. Write or generate failing test.
2. Run targeted suite.
3. Implement minimal fix.
4. Re-run tests.
5. Refactor.
6. Regenerate docs and AI context.

## Test Inventory

### Frontend

${listToBullet(facts.frontend.specs)}

### Backend

${listToBullet(facts.backend.tests)}

## Regression Policy

- Every production bug should add a regression test.
- Contract changes must update docs and RAG index in the same pull request.
`;
}

function buildAiAgentsDoc() {
  return `# AI Agents

Generated from repository state on ${facts.generatedAt}.

## Agent Operating Model

- Primary generated context lives in \`ai-context/\`.
- Retrieval index is produced by \`scripts/build-rag-index.mjs\`.
- Reusable prompts live in \`prompts/\`.
- CI regenerates docs and the RAG index on every relevant push or pull request.

## Preferred Agent Flow

1. Query \`ai-context/rag-index.json\` through \`scripts/query-rag.mjs\`.
2. Read linked source files before proposing changes.
3. Write tests before implementation when behavior changes.
4. Regenerate docs after code changes.

## Known Limits

- Retrieval is local JSON-based by default.
- Embeddings are deterministic hashed vectors generated locally to keep the system dependency-light.
`;
}

function buildTroubleshootingDoc() {
  return `# Troubleshooting

Generated from repository state on ${facts.generatedAt}.

## Known Repository Issues

- Backend CI references Maven wrapper files that are missing from the current repository snapshot.
- Backend endpoint extraction is incomplete because controller classes are not present in tracked source.
- Frontend route \`Ususrios\` looks like a typo and should be reviewed before exposing it externally.

## Recovery Actions

- Restore backend build files before enabling mandatory backend CI gates.
- Add controller sources or OpenAPI export to improve API documentation fidelity.
- Keep generated docs committed so AI agents and humans read the same system state.
`;
}

writeFile('docs/architecture.md', buildArchitectureDoc());
writeFile('docs/api.md', buildApiDoc());
writeFile('docs/workflows.md', buildWorkflowsDoc());
writeFile('docs/development.md', buildDevelopmentDoc());
writeFile('docs/testing.md', buildTestingDoc());
writeFile('docs/ai-agents.md', buildAiAgentsDoc());
writeFile('docs/troubleshooting.md', buildTroubleshootingDoc());

writeFile(
  'adr/README.md',
  `# Architecture Decision Records

Store one decision per file using the naming pattern \`NNNN-short-title.md\`.

- \`0001-docs-automation-foundation.md\` establishes the initial docs, RAG and AI-TDD automation.
- New decisions should link affected code, docs and workflows.
`,
);

writeFile(
  'adr/0001-docs-automation-foundation.md',
  `# ADR 0001: Docs and AI Automation Foundation

## Status

Accepted

## Context

The repository contains code, tests and documentation, but the system state is fragmented across folders and is not automatically synchronized for developer workflows or AI agents.

## Decision

Use dependency-light Node scripts in the repository root to:

- generate canonical docs in \`docs/*.md\`
- generate AI context in \`ai-context/*.md\`
- build a local JSON retrieval index
- validate that required automation artifacts exist

## Consequences

- CI can regenerate documentation without additional services.
- AI agents get a stable knowledge surface in git.
- Semantic embeddings can be added later without replacing the current retrieval layer.
`,
);

writeFile(
  'docs/diagrams/system-context.puml',
  `@startuml
!include <C4/C4_Context>

Person(developer, "Developer")
Person(aiAgent, "AI Agent")
System(coco, "COCO", "Project and task management system")
System_Ext(github, "GitHub Actions", "CI/CD and automation")
System_Ext(rag, "Local RAG Index", "Generated JSON retrieval store")

Rel(developer, coco, "Builds and operates")
Rel(aiAgent, rag, "Queries")
Rel(rag, coco, "Indexes code and docs")
Rel(github, coco, "Validates and regenerates artifacts")

@enduml
`,
);

writeFile(
  'docs/diagrams/container-view.puml',
  `@startuml
!include <C4/C4_Container>

Person(developer, "Developer")
System_Boundary(coco, "COCO") {
  Container(frontend, "Frontend", "Angular", "User interface and routing")
  Container(backend, "Backend", "Java", "Business logic and security services")
  ContainerDb(db, "PostgreSQL", "SQL", "Persistent storage")
  Container(docs, "Docs Automation", "Node.js scripts", "Generates docs, AI context and RAG index")
}

Rel(developer, frontend, "Uses")
Rel(frontend, backend, "Calls APIs")
Rel(backend, db, "Reads/Writes")
Rel(docs, frontend, "Scans")
Rel(docs, backend, "Scans")
Rel(docs, db, "Documents schema from migrations")

@enduml
`,
);

writeFile(
  'docs/diagrams/component-view.puml',
  `@startuml
package "Automation" {
  [generate-docs.mjs]
  [generate-ai-context.mjs]
  [build-rag-index.mjs]
  [query-rag.mjs]
  [validate-system.mjs]
}

[generate-docs.mjs] --> [generate-ai-context.mjs]
[generate-ai-context.mjs] --> [build-rag-index.mjs]
[query-rag.mjs] --> [build-rag-index.mjs]
[validate-system.mjs] --> [generate-docs.mjs]

@enduml
`,
);

console.log('Generated docs and ADR artifacts.');
