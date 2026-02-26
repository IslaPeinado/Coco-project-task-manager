# Documentacion Tecnica - COCO

Esta carpeta esta organizada para entender rapido el proyecto desde una perspectiva tecnica.

## Lectura recomendada

1. `architecture/overview.md`
2. `architecture/improvement-roadmap.md`
3. `backend/architecture.md`
4. `frontend/architecture.md`
5. `database/schema.md`
6. `security/security-architecture.md`
7. `operations/runbook.md`
8. `design/README.md`

## Estructura

```text
docs/
|- README.md
|- architecture/
|  |- overview.md
|  |- improvement-roadmap.md
|  `- diagrams/
|- backend/
|  `- architecture.md
|- frontend/
|  |- architecture.md
|  `- diagrams/
|- database/
|  |- README.md
|  |- schema.md
|  `- diagrams/
|- security/
|  `- security-architecture.md
|- operations/
|  `- runbook.md
`- design/
   |- README.md
   |- design-system/
   |- mockups/
   `- assets/
```

## Mantenimiento

- `main` se mantiene como rama virgen de referencia.
- `dev` es la rama base activa para desarrollo.
- Si cambia arquitectura, actualizar:
  - `architecture/overview.md`
  - `architecture/improvement-roadmap.md`
  - documentacion tecnica afectada.