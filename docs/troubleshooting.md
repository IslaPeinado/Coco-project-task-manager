# Troubleshooting

Generated from repository state.

## Known Repository Issues

- Backend CI references Maven wrapper files that are missing from the current repository snapshot.
- Backend endpoint extraction is incomplete because controller classes are not present in tracked source.
- Frontend route `Ususrios` looks like a typo and should be reviewed before exposing it externally.

## Recovery Actions

- Restore backend build files before enabling mandatory backend CI gates.
- Add controller sources or OpenAPI export to improve API documentation fidelity.
- Keep generated docs committed so AI agents and humans read the same system state.
