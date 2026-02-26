# Runbook Operativo

## Estrategia de ramas

- `main`: rama virgen de referencia.
- `dev`: rama base activa para integracion.
- ramas por dominio (`backend/*`, `frontend/*`) derivadas desde `dev`.

## Ejecucion local

### Backend

```bash
cd backend
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

### Frontend

```bash
cd frontend
npm install
npm run start
```

## Validaciones minimas antes de PR

### Backend

```bash
cd backend
./mvnw test
./mvnw -DskipTests package
```

### Frontend

```bash
cd frontend
npm ci
npm run lint --if-present
npm test -- --watch=false
npm run build
```

## CI en GitHub Actions

- Backend: `.github/workflows/ci-backend.yml`
- Frontend: `.github/workflows/ci-frontend.yml`
- Accion reusable frontend: `.github/actions/action.yml`

## Criterio de merge a `dev`

- CI en verde.
- Impacto tecnico documentado en PR.
- Sin secretos en codigo, logs o capturas.
- Documentacion actualizada si cambia contrato o arquitectura.