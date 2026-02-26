## COCO Agent Contract (Machine-Oriented)

### 1) Project Identity
- repo_root: `Coco-project-task-manager`
- purpose: `generic router + global rules for any AI agent`
- canonical_specialized_agents:
  - `agents/backend-agent.md`
  - `agents/frontend-agant.md`

### 2) Repository Topology
- backend_root: `backend/`
- frontend_root: `frontend/`
- docs_root: `README.md`
- shared_agent_root: `agents/`

### 3) Routing Contract (Mandatory)
- if scope is backend-only -> apply `agents/backend-agent.md`.
- if scope is frontend-only -> apply `agents/frontend-agant.md`.
- if scope is full-stack:
  1. change backend contract and implementation first;
  2. adapt frontend to resulting API/auth/error contracts;
  3. run validations on both layers.
- precedence rule:
  - specialized contract overrides this generic contract for its layer.

### 4) Task Classification Rules
- backend tasks include:
  - API endpoints, security, persistence, migrations, Java business logic.
- frontend tasks include:
  - Angular routes, components, services, guards, interceptors, UI state.
- cross-cutting tasks include:
  - auth flow, DTO/JSON contract updates, role/permission behavior, error envelopes.

### 5) Global Change Protocol For Agents
1. Identify impacted layer(s): `backend`, `frontend`, or both.
2. Load and follow the specialized contract(s).
3. Apply minimal changes only for requested behavior.
4. Preserve current naming, architecture, and compatibility constraints.
5. Validate with layer-specific commands before finalizing.
6. Report modified files + validation status + known limitations.

### 6) Global Quality Rules
- do not invent new architecture patterns without explicit request.
- do not rename directories/entrypoints unless requested.
- do not break existing API/auth/error contracts unintentionally.
- avoid unrelated refactors in task-scoped changes.
- keep conventions consistent with current codebase reality.

### 7) Validation Contract
- backend touched -> run backend validations defined in `agents/backend-agent.md`.
- frontend touched -> run frontend validations defined in `agents/frontend-agant.md`.
- full-stack touched -> validate both layers and contract compatibility.
- if validation cannot run, document exactly what was not executed and why.

### 8) Completion Criteria
- requested behavior implemented in all impacted layers.
- specialized contracts were respected:
  - `agents/backend-agent.md`
  - `agents/frontend-agant.md`
- validation status is explicit (`passed` / `failed` / `not run with reason`).
