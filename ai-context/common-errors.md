# Common Errors

Generated on 2026-03-16T13:25:27.217Z.

## Repository-Specific Risks

- CI may fail on backend jobs until Maven or Gradle wrapper files are restored.
- Generated docs may drift if contributors skip `npm run automation:sync` locally.
- Route naming typos can propagate into docs and prompts because generators read source of truth directly.
