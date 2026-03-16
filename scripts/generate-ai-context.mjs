import { collectProjectFacts, listToBullet, writeFile } from './lib/project-analysis.mjs';

const facts = collectProjectFacts();

writeFile(
  'ai-context/system-overview.md',
  `# System Overview

Generated from repository state.

## Purpose

COCO is a portfolio-oriented full-stack project manager with Angular frontend assets, Java backend domain/security code, SQL migrations and repository-level automation.

## High-Signal Facts

- Frontend routes: ${facts.frontend.routes.map((route) => `\`${route}\``).join(', ') || 'none detected'}
- Frontend spec count: ${facts.frontend.specs.length}
- Backend public types: ${facts.backend.types.map((type) => `\`${type.name}\``).join(', ') || 'none detected'}
- Migration count: ${facts.backend.migrations.length}

## Critical Caveats

- Backend build files are ${facts.stack.backendBuildPresent ? 'present' : 'missing'} in this repo snapshot.
- Endpoint extraction is only reliable when controller sources or OpenAPI exports are available.
`,
);

writeFile(
  'ai-context/domain-knowledge.md',
  `# Domain Knowledge

Generated from repository state.

## Business Areas

- Authentication and JWT-based access control
- Project membership and role handling
- Task management with normalized status data

## Evidence In Repository

${listToBullet([
  ...facts.backend.types.map((type) => `${type.name} in ${type.file}`),
  ...facts.backend.migrations,
])}
`,
);

writeFile(
  'ai-context/api-reference.md',
  `# API Reference

Generated from repository state.

## Frontend Route Entry Points

${listToBullet(facts.frontend.routes)}

## Backend Types Related To API/Security

${listToBullet(facts.backend.types.map((type) => `${type.name} (${type.kind})`))}

## Gap Notes

- No Spring controller annotations were detected in the tracked Java sources.
- When backend endpoints are restored, extend the generator to emit endpoint-level chunks.
`,
);

writeFile(
  'ai-context/common-errors.md',
  `# Common Errors

Generated from repository state.

## Repository-Specific Risks

- CI may fail on backend jobs until Maven or Gradle wrapper files are restored.
- Generated docs may drift if contributors skip \`npm run automation:sync\` locally.
- Route naming typos can propagate into docs and prompts because generators read source of truth directly.
`,
);

console.log('Generated AI context files.');
