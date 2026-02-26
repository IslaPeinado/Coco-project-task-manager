# Security Policy - COCO

Este repositorio forma parte de mi portfolio y trato la seguridad con criterios reales de proyecto: controles tecnicos, trazabilidad y mejora continua.

## Estrategia de ramas

- `main`: rama virgen de referencia. No se usa para trabajo diario.
- `dev`: rama base activa. Aqui se integran cambios, incluidos fixes de seguridad.

Todo hallazgo de seguridad se corrige primero en `dev` y despues se promueve a `main`.

## Controles de seguridad aplicados

- Autenticacion JWT stateless.
- Autorizacion por roles y contexto de proyecto.
- Endpoints protegidos por defecto en Spring Security.
- Manejo global de errores para evitar exposicion de detalles internos.
- Separacion por capas (`api`, `application`, `domain`, `infra`) para reducir acoplamiento.

## Reporte responsable de vulnerabilidades

No publiques vulnerabilidades en issues abiertos.

1. Abre un reporte privado:
   - https://github.com/IslaPeinado/Coco-project-task-manager/security/advisories/new
2. Incluye:
   - descripcion tecnica;
   - pasos de reproduccion;
   - impacto;
   - mitigacion propuesta (si aplica).

Reviso y priorizo los reportes segun severidad y riesgo.

## Compromiso

La seguridad en este proyecto no es un check puntual: es parte continua del ciclo de desarrollo.