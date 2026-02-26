# Security Policy - COCO

Este proyecto forma parte de un portfolio tecnico y busca demostrar buenas practicas reales de seguridad en una aplicacion fullstack moderna.

## Enfoque de seguridad del proyecto

COCO esta disenado con una base de seguridad orientada a entornos empresariales:

- Autenticacion stateless con JWT en backend.
- Autorizacion basada en roles y contexto de proyecto.
- Endpoints protegidos por defecto en Spring Security.
- Manejo centralizado de errores para evitar fugas de informacion sensible.
- Separacion por capas (api, application, domain, infra) para limitar acoplamiento y riesgo.

## Alcance y version soportada

Al tratarse de un proyecto portfolio, se mantiene activa la rama principal:

- Rama soportada para reportes de seguridad: `main`.

## Gestion responsable de vulnerabilidades

Si detectas una vulnerabilidad, no la publiques en un issue abierto.

1. Crea un reporte privado en:
   - https://github.com/IslaPeinado/Coco-project-task-manager/security/advisories/new
2. Incluye:
   - descripcion tecnica del problema;
   - pasos de reproduccion;
   - impacto potencial;
   - propuesta de mitigacion (si aplica).
3. Se dara seguimiento y se priorizara la correccion en funcion del riesgo.

## Compromiso de mejora continua

Este repositorio evoluciona de forma incremental. Las mejoras de seguridad (hardening, cobertura de tests, configuracion y control de acceso) forman parte del proceso normal de iteracion del proyecto.
