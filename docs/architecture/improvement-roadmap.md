# Roadmap de Mejora Arquitectonica

## Fase 1 - Quick wins

1. Versionar API y estandarizar respuestas de error.
2. Completar flujo de auth frontend (login, guard, redirecciones).
3. Fortalecer CI para validar contrato OpenAPI y builds cruzadas.

## Fase 2 - Consolidacion

1. Observabilidad minima:
   - logs estructurados;
   - metricas de negocio;
   - correlacion con `traceId`.
2. Mejorar cobertura de tests en auth, permisos y tareas.
3. Homogeneizar runbook y checklist tecnico de PR.

## Fase 3 - Escalado

1. Cache selectiva para lecturas frecuentes.
2. Auditoria de eventos de seguridad y negocio.
3. Hardening avanzado:
   - rate limiting;
   - rotacion de secretos;
   - CORS estricto por entorno.

## Criterio de exito

- Menor deuda tecnica en flujos criticos.
- Contratos API mas estables.
- Menor riesgo de regresiones en CI.
- Mejor capacidad de diagnostico en incidencias.