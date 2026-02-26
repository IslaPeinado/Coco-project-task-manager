## Que cambia y por que
Describe el objetivo del PR, el problema que resuelve y el impacto esperado.

## Tipo de cambio
- [ ] FEATURE
- [ ] FIX
- [ ] REFACTOR
- [ ] TEST
- [ ] DOCS
- [ ] CHORE / STYLE / PERF

## Cambios principales
- [ ] Backend (casos de uso, controllers, seguridad, persistencia)
- [ ] Frontend
- [ ] Base de datos (scripts SQL / migraciones)
- [ ] Infra/CI
- [ ] Documentacion

## Evidencia de pruebas
Marca lo que aplica y pega comandos/salidas relevantes.

- [ ] Unit tests (`@ExtendWith(MockitoExtension.class)`)
- [ ] Web/API security tests (`@WebMvcTest` + `MockMvc` + `@WithMockUser`)
- [ ] Integration tests (`@SpringBootTest` / `AbstractIntegrationTest`)
- [ ] Probe en local


## Checklist tecnico
- [ ] Si cambie contrato API, actualice OpenAPI/Swagger y DTOs
- [ ] Si cambie validaciones/errores, revise `GlobalExceptionHandler` y codigos de error
- [ ] Si cambie DB, actualice scripts necesarios (ej: `infra/cocoDB.sql` y/o `db/migration`)
- [ ] No rompo compatibilidad hacia atras sin documentarlo
- [ ] Actualice README/docs si aplica

## Seguridad (JWT + Spring Security)
- [ ] Endpoints nuevos quedan protegidos por defecto (`anyRequest().authenticated()`) o justifique excepcion
- [ ] Si habilite endpoint publico (ej. `/api/auth/**`), esta justificado y documentado
- [ ] Mantengo flujo stateless y filtro JWT (`JwtAuthFilter`) en el orden correcto
- [ ] No expongo secretos/tokens/claims en codigo, logs, capturas ni descripcion del PR
- [ ] Si cambie permisos/roles/reglas de acceso, lo cubri con tests
- [ ] Si toque CORS, documente propiedades (`coco.cors.*`) y origenes permitidos

## Riesgos y plan de rollback
Describe riesgos tecnicos, impacto potencial y como revertir si falla en deploy.

## Issue relacionada
Closes #
Related #
