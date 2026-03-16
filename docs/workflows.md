# Workflows

Generated from repository state.

## Feature Workflow

1. Capture requirement in issue or ADR.
2. Generate or update unit and integration tests first.
3. Implement the smallest code change that passes tests.
4. Run frontend or backend validation.
5. Regenerate docs and AI context.
6. Commit code and generated artifacts together.

## API Workflow

1. Define contract in `docs/api.md` or OpenAPI.
2. Add contract or security tests.
3. Implement endpoint and persistence changes.
4. Update migrations if required.
5. Regenerate architecture and AI docs.

## Debug Workflow

1. Reproduce failure with a deterministic test.
2. Capture root cause in `docs/troubleshooting.md` or a regression test.
3. Patch minimally.
4. Re-run validation and regenerate artifacts.
