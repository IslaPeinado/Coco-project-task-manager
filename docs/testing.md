# Testing

Generated from repository state.

## Current Coverage Signals

- Frontend spec files: 22
- Backend test files: 5

## AI-TDD Cycle

1. Write or generate failing test.
2. Run targeted suite.
3. Implement minimal fix.
4. Re-run tests.
5. Refactor.
6. Regenerate docs and AI context.

## Test Inventory

### Frontend

- `frontend/src/app/app.spec.ts`
- `frontend/src/app/core/guards/auth-guard.spec.ts`
- `frontend/src/app/core/interceptors/auth-interceptor.spec.ts`
- `frontend/src/app/core/services/auth/auth.service.spec.ts`
- `frontend/src/app/core/services/token/token.service.spec.ts`
- `frontend/src/app/shared/components/alert/alert.spec.ts`
- `frontend/src/app/shared/components/avatar/avatar.spec.ts`
- `frontend/src/app/shared/components/badge/badge.spec.ts`
- `frontend/src/app/shared/components/button/button.spec.ts`
- `frontend/src/app/shared/components/card/card.spec.ts`
- `frontend/src/app/shared/components/checkbox/checkbox.spec.ts`
- `frontend/src/app/shared/components/empty-state/empty-state.spec.ts`
- `frontend/src/app/shared/components/footer/footer.spec.ts`
- `frontend/src/app/shared/components/icon-button/icon-button.spec.ts`
- `frontend/src/app/shared/components/input/coco-input.spec.ts`
- `frontend/src/app/shared/components/modal/modal.spec.ts`
- `frontend/src/app/shared/components/progress/progress.spec.ts`
- `frontend/src/app/shared/components/select/select.spec.ts`
- `frontend/src/app/shared/components/stat-card/stat-card.spec.ts`
- `frontend/src/app/shared/components/table/table.spec.ts`
- `frontend/src/app/shared/components/tabs/tabs.spec.ts`
- `frontend/src/app/shared/components/toast/toast.spec.ts`

### Backend

- `backend/src/test/java/com/coco/modules/project/api/ProjectMembersControllerSecurityIntegrationTest.java`
- `backend/src/test/java/com/coco/modules/task/api/TaskControllerSecurityIntegrationTest.java`
- `backend/src/test/java/com/coco/modules/user/application/user/GetMeUseCaseTest.java`
- `backend/src/test/java/com/coco/modules/user/application/user/GetUserUseCaseTest.java`
- `backend/src/test/java/com/coco/security/jwt/JwtServiceTest.java`

## Regression Policy

- Every production bug should add a regression test.
- Contract changes must update docs and RAG index in the same pull request.
