## Que cambia y por que
Describe el objetivo del PR, el problema que resuelve y el impacto esperado en la UI o en el comportamiento del frontend.

## Tipo de cambio
- [ ] FEATURE
- [ ] FIX
- [ ] REFACTOR
- [ ] TEST
- [ ] DOCS
- [ ] CHORE / STYLE / PERF

## Alcance frontend
- [ ] Layouts
- [ ] Routing / guards
- [ ] Feature pages
- [ ] Shared components
- [ ] Design tokens / estilos globales
- [ ] Formularios
- [ ] Estado / servicios / interceptors
- [ ] Accesibilidad
- [ ] Responsive

## Cambios principales
Resume los cambios relevantes.

- Rutas / pantallas afectadas:
- Componentes compartidos afectados:
- Servicios / guards / interceptors afectados:
- Tokens / estilos globales afectados:

## Evidencia visual
Adjunta capturas, GIFs o video corto si el cambio impacta UI.

- [ ] No aplica
- [ ] Desktop
- [ ] Mobile
- [ ] Estados vacio / loading / error
- [ ] Tema light
- [ ] Tema dark

## Evidencia de pruebas
Marca lo que aplica y pega comandos o notas relevantes.

- [ ] Unit tests (`ng test`)
- [ ] Build local (`npm run build`)
- [ ] Prueba manual en local (`npm start`)
- [ ] Verifique navegacion/rutas afectadas
- [ ] Verifique formularios y validaciones afectadas
- [ ] Verifique responsive
- [ ] Verifique accesibilidad basica (focus, teclado, labels, contraste)

## Checklist tecnico
- [ ] Mantengo arquitectura frontend (`core/`, `features/`, `layout/`, `shared/`)
- [ ] Si agregue componentes reutilizables, los ubique en `shared/`
- [ ] Si cambie estilos base, reutilice tokens globales y evite estilos inline
- [ ] Si cambie formularios, cubri estados `disabled`, `error`, `loading` si aplica
- [ ] Si cambie rutas privadas, revise `authGuard` / `authInterceptor`
- [ ] Si cambie consumo de API, documente impacto en contratos o mocks
- [ ] No dejo placeholders, logs o codigo muerto sin justificar
- [ ] Actualice docs/README si aplica

## Riesgos y plan de rollback
Describe riesgos tecnicos o visuales, impacto potencial y como revertir si falla.

## Issue relacionada
Closes #
Related #
