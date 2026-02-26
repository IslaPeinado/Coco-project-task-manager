# Security Policy - COCO

Este repositorio es parte de mi portfolio y quiero tratar la seguridad como lo haria en un proyecto real: con criterio, trazabilidad y mejora continua.

## Como gestiono las ramas

- `main`: snapshot estable del proyecto. No se usa como rama de trabajo diario.
- `dev`: rama base activa. Aqui integro cambios, correcciones y mejoras de seguridad antes de promover a `main`.

Por eso, cualquier hallazgo de seguridad se analiza y corrige sobre `dev`, y solo despues se refleja en `main`.

## Enfoque de seguridad del proyecto

En COCO estoy aplicando practicas que considero clave:

- autenticacion stateless con JWT;
- autorizacion por roles y contexto de proyecto;
- endpoints protegidos por defecto en Spring Security;
- manejo global de errores para evitar exponer informacion sensible;
- separacion por capas (`api`, `application`, `domain`, `infra`) para reducir acoplamiento y riesgo.

## Como reportar una vulnerabilidad

Si detectas una vulnerabilidad, por favor no la publiques en un issue abierto.

1. Abre un reporte privado en:
   - https://github.com/IslaPeinado/Coco-project-task-manager/security/advisories/new
2. Incluye:
   - descripcion tecnica del problema;
   - pasos para reproducirlo;
   - impacto estimado;
   - posible mitigacion (si la tienes).

Revisare el reporte, priorizare segun severidad y hare seguimiento hasta dejarlo corregido en `dev`.

## Compromiso

Este proyecto evoluciona de forma iterativa. La seguridad no es un check unico: es parte constante del ciclo de desarrollo.
