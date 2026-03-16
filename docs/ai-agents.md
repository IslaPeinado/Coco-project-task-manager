# AI Agents

Generated from repository state on 2026-03-16T13:25:26.764Z.

## Agent Operating Model

- Primary generated context lives in `ai-context/`.
- Retrieval index is produced by `scripts/build-rag-index.mjs`.
- Reusable prompts live in `prompts/`.
- CI regenerates docs and the RAG index on every relevant push or pull request.

## Preferred Agent Flow

1. Query `ai-context/rag-index.json` through `scripts/query-rag.mjs`.
2. Read linked source files before proposing changes.
3. Write tests before implementation when behavior changes.
4. Regenerate docs after code changes.

## Known Limits

- Retrieval is local JSON-based by default.
- Embeddings are deterministic hashed vectors generated locally to keep the system dependency-light.
