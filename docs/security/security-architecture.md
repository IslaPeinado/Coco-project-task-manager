# Arquitectura de Seguridad

## Alcance

Describe controles de seguridad actuales y hardening recomendado para evolucion del proyecto.

## Autenticacion y autorizacion

- Modelo stateless con JWT Bearer.
- Endpoints publicos limitados (`/api/auth/**`, docs y health checks).
- Endpoints restantes protegidos por autenticacion.

Base tecnica:
- `SecurityFilterChain`
- `JwtAuthFilter`
- `SessionCreationPolicy.STATELESS`

## Control de acceso de negocio

- Permisos por proyecto evaluados en casos de uso.
- Rechazo `403` cuando no existe permiso suficiente.

## Passwords y tokens

- Hashing con BCrypt.
- JWT configurable por propiedades:
  - `coco.jwt.secret`
  - `coco.jwt.expiration-seconds`
  - `coco.jwt.issuer`

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