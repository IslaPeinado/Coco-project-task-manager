# Arquitectura de Seguridad

## Alcance

Describe controles de seguridad actuales y hardening recomendado para evolucion del proyecto.

## Autenticacion y autorizacion

- Modelo stateless con JWT Bearer.
- Endpoints publicos limitados (`/api/auth/**`, docs y health checks).
- Endpoints restantes protegidos por autenticacion.

### Estrategia RBAC final

- Se adopta `RBAC por proyecto` como modelo unico de autorizacion de negocio.
- No se usan roles globales de negocio (`USER/ADMIN`) para conceder permisos funcionales.
- Los permisos se resuelven por membresia de proyecto (`OWNER/MANAGER/MEMBER/VIEWER`) en capa de aplicacion.

Base tecnica:
- `SecurityFilterChain`
- `JwtAuthFilter`
- `SessionCreationPolicy.STATELESS`

## Control de acceso de negocio

- Permisos por proyecto evaluados en casos de uso.
- Rechazo `403` cuando no existe permiso suficiente.
- Politica centralizada en `ProjectPolicy` y aplicada desde `ProjectAuthorizationService`.

## Passwords y tokens

- Hashing con BCrypt.
- JWT configurable por propiedades:
  - `coco.jwt.secret`
  - `coco.jwt.expiration-seconds`
  - `coco.jwt.issuer`
- Claims JWT de autorizacion:
  - `authorities: ["AUTHENTICATED"]` (autoridad tecnica para contexto Spring Security).
  - `authz_model: "PROJECT_MEMBERSHIP"` (declaracion explicita del modelo de autorizacion).
- El JWT no serializa permisos por proyecto porque dependen del `projectId` de cada request y se validan contra membresias persistidas.

## Riesgos actuales

1. Sin rotacion automatizada de secretos.
2. Sin revocacion centralizada de tokens.
3. Auditoria de eventos aun basica.

## Hardening recomendado

1. Rotacion de secretos por entorno.
2. Auditoria estructurada de eventos sensibles.
3. CORS estricto por entorno.
4. Rate limiting en endpoints de auth.

## Referencias

- Politica del repo: `../../SECURITY.md`
- Backend: `../backend/architecture.md`
- Runbook: `../operations/runbook.md`
