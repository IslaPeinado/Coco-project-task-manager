# Common Errors

Generated from repository state.

## Repository-Specific Risks

- CI may fail on backend jobs until Maven or Gradle wrapper files are restored.
- Generated docs may drift if contributors skip `npm run automation:sync` locally.
- Route naming typos can propagate into docs and prompts because generators read source of truth directly.
