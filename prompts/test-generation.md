# Test Generation Prompt

Generate tests for the selected change using AI-TDD.

Rules:

- Prefer unit tests first.
- Add integration or contract tests when public behavior changes.
- Name the regression scenario explicitly when fixing a bug.
- Keep mocks minimal and assert observable behavior.
- Update `docs/testing.md` inputs by running `npm run automation:sync`.
