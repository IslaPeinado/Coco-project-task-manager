# COCO - Database Documentation (PostgreSQL)

## 1. Alcance

Este documento describe el esquema **real** aplicado por Flyway segun los scripts disponibles en:

- `backend/src/main/resources/db/migration/V1__.sql`
- `backend/src/main/resources/db/migration/V2__projects_archived_at.sql`
- `backend/src/main/resources/db/migration/V3__normalize_project_roles.sql`
- `backend/src/main/resources/db/migration/V4__seed_roles_and_task_status.sql`

## 2. Historial de migraciones

| Version | Archivo | Objetivo |
|---|---|---|
| V1 | `V1__.sql` | Crea esquema inicial: usuarios, proyectos, roles, membresias, tareas y catalogo de estados de tarea. |
| V2 | `V2__projects_archived_at.sql` | Agrega `project.archived_at` e indice por `status, archived_at`. |
| V3 | `V3__normalize_project_roles.sql` | Normaliza roles de proyecto a `OWNER/MANAGER/MEMBER/VIEWER` y migra `ADMIN -> OWNER`. |
| V4 | `V4__seed_roles_and_task_status.sql` | Siembra catalogos base idempotentes: roles por proyecto y estados de tarea (`TODO`, `IN_PROGRESS`, `DONE`). |

## 3. Modelo relacional

Relaciones principales:

- `project` 1:N `tasks`
- `cocouser` N:M `project` via `cocouser_project_role`
- `role` 1:N `cocouser_project_role`
- `task_status` 1:N `tasks`
- `cocouser` 1:N `tasks` (asignacion opcional)

## 4. Tablas

### 4.1 `cocouser`

| Columna | Tipo | Nulo | Default | Notas |
|---|---|---|---|---|
| id | BIGINT | NO | identity | PK |
| login | VARCHAR(255) | NO | - | UNIQUE |
| password | VARCHAR(255) | NO | - | hash de password |
| first_name | VARCHAR(255) | SI | - | |
| last_name | VARCHAR(255) | SI | - | |
| email | VARCHAR(255) | NO | - | UNIQUE |
| image_url | TEXT | SI | - | |
| created_at | TIMESTAMP WITHOUT TIME ZONE | NO | NOW() | |
| updated_at | TIMESTAMP WITHOUT TIME ZONE | NO | NOW() | |

Constraints:

- `cocouser_pkey`
- `cocouser_login_key`
- `cocouser_email_key`

### 4.2 `project`

| Columna | Tipo | Nulo | Default | Notas |
|---|---|---|---|---|
| id | BIGINT | NO | identity | PK |
| name | VARCHAR(255) | NO | - | UNIQUE |
| description | TEXT | SI | - | |
| logo_url | TEXT | SI | - | |
| status | VARCHAR(50) | NO | 'ACTIVE' | sin CHECK en DB |
| created_at | TIMESTAMP WITHOUT TIME ZONE | NO | NOW() | |
| updated_at | TIMESTAMP WITHOUT TIME ZONE | NO | NOW() | |
| archived_at | TIMESTAMPTZ | SI | NULL | agregado en V2 |

Constraints / indices:

- `project_pkey`
- `project_name_key`
- `idx_projects_status_archived_at` (V2)

### 4.3 `role`

| Columna | Tipo | Nulo | Default | Notas |
|---|---|---|---|---|
| id | BIGINT | NO | identity | PK |
| role_name | VARCHAR(255) | NO | - | UNIQUE |

Constraints:

- `role_pkey`
- `role_role_name_key`

### 4.4 `cocouser_project_role`

Tabla puente de membresia/rol por proyecto.

| Columna | Tipo | Nulo | Notas |
|---|---|---|---|
| user_id | BIGINT | NO | FK -> `cocouser(id)` |
| project_id | BIGINT | NO | FK -> `project(id)` |
| role_id | BIGINT | NO | FK -> `role(id)` |

PK y FKs:

- PK: `(user_id, project_id)`
- `fk_upr_user` (`ON DELETE CASCADE`)
- `fk_upr_project` (`ON DELETE CASCADE`)
- `fk_upr_role` (`ON DELETE CASCADE`)

Indices:

- `idx_upr_user_id`
- `idx_upr_project_id`
- `idx_upr_role_id`

### 4.5 `task_status`

Catalogo de estados de tarea.

| Columna | Tipo | Nulo | Default | Notas |
|---|---|---|---|---|
| status | VARCHAR(50) | NO | - | PK |
| display_name | VARCHAR(100) | NO | - | |
| color_hex | CHAR(7) | NO | - | |
| sort_order | SMALLINT | NO | 0 | |
| is_terminal | BOOLEAN | NO | FALSE | |

Constraints / indices:

- `task_status_pkey`
- `idx_task_status_sort_order`

### 4.6 `tasks`

| Columna | Tipo | Nulo | Default | Notas |
|---|---|---|---|---|
| id | BIGINT | NO | identity | PK |
| project_id | BIGINT | NO | - | FK -> `project(id)` |
| title | VARCHAR(255) | NO | - | |
| description | TEXT | SI | - | |
| status | VARCHAR(50) | NO | - | FK -> `task_status(status)` |
| assigned_to | BIGINT | SI | - | FK -> `cocouser(id)` |
| due_date | DATE | SI | - | |
| created_at | TIMESTAMP WITHOUT TIME ZONE | NO | NOW() | |
| updated_at | TIMESTAMP WITHOUT TIME ZONE | NO | NOW() | |

FKs y reglas de borrado:

- `fk_tasks_project`: `project_id -> project(id)` (`ON DELETE CASCADE`)
- `fk_tasks_status`: `status -> task_status(status)` (`ON DELETE RESTRICT`)
- `fk_tasks_assignee`: `assigned_to -> cocouser(id)` (`ON DELETE SET NULL`)

Indices:

- `idx_tasks_project_id`
- `idx_tasks_status`
- `idx_tasks_assigned_to`


