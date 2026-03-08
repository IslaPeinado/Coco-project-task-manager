# Arquitectura de Datos (PostgreSQL)

## Alcance

Este documento describe el modelo relacional actual y mejoras recomendadas.

Migraciones base:
- `V1__.sql`
- `V2__projects_archived_at.sql`
- `V3__normalize_project_roles.sql`
- `V4__seed_roles_and_task_status.sql`

## Entidades principales

- `cocouser`
- `project`
- `role`
- `cocouser_project_role`
- `task_status`
- `tasks`

Roles canonicos por proyecto:
- `OWNER`
- `MANAGER`
- `MEMBER`
- `VIEWER`

## Relaciones clave

- `project` 1:N `tasks`
- `cocouser` N:M `project` via `cocouser_project_role`
- `task_status` 1:N `tasks`
- `cocouser` 1:N `tasks` (asignacion opcional)

## Reglas de integridad

- Cascade en membresias de proyecto.
- Restriccion de borrado para estados de tarea en uso.
- `assigned_to` con `SET NULL`.

## Riesgos actuales

1. Naming no totalmente uniforme.
2. Sin auditoria de cambios sensibles.
3. Sin estrategia de retencion/archivado historico.

## Mejoras recomendadas

1. Convencion de naming documentada.
2. Auditoria de eventos de negocio.
3. Indices adicionales para consultas frecuentes.
4. Constraints de negocio donde aporte robustez.

## Referencias

- Diagrama ER: `diagrams/database-schema.puml`
- Arquitectura global: `../architecture/overview.md`
