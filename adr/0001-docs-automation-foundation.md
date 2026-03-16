# ADR 0001: Docs and AI Automation Foundation

## Status

Accepted

## Context

The repository contains code, tests and documentation, but the system state is fragmented across folders and is not automatically synchronized for developer workflows or AI agents.

## Decision

Use dependency-light Node scripts in the repository root to:

- generate canonical docs in `docs/*.md`
- generate AI context in `ai-context/*.md`
- build a local JSON retrieval index
- validate that required automation artifacts exist

## Consequences

- CI can regenerate documentation without additional services.
- AI agents get a stable knowledge surface in git.
- Semantic embeddings can be added later without replacing the current retrieval layer.
