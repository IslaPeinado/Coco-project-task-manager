# 📦 COCO – Database Documentation (PostgreSQL)

## 1. Visión general

La base de datos de **COCO** está diseñada para dar soporte a una aplicación de gestión de proyectos y tareas con control de acceso por roles a nivel de proyecto.  
El modelo sigue principios de **normalización**, **integridad referencial** y **auditabilidad**, y está preparado para escalar sin introducir complejidad innecesaria.

Características principales:

- Gestión de usuarios con credenciales y metadatos
- Proyectos con estados controlados
- Asignación de roles por usuario y proyecto (RBAC)
- Tareas con estados configurables (catálogo)
- Timestamps automáticos (`created_at`, `updated_at`)
- Reglas claras de borrado (`CASCADE`, `SET NULL`, `RESTRICT`)

---

## 2. Diagrama

- Un **usuario** puede pertenecer a **muchos proyectos**
- Un **proyecto** puede tener **muchos usuarios**
- Cada usuario tiene **un único rol por proyecto**
- Un **proyecto** tiene **muchas tareas**
- Una **tarea** puede estar asignada a **un usuario o ninguno**
- El estado de una tarea pertenece a un **catálogo controlado**

![alt text](diagrama_base_datos.png)

---

## 3. Tablas


### 3.1 `cocoUser`

Tabla de usuarios del sistema.

| Columna | Tipo | Nulo | Descripción |
|------|------|------|-------------|
| id | BIGINT | NO | Identificador único |
| login | VARCHAR(255) | NO | Nombre de usuario (único) |
| password | VARCHAR(255) | NO | Hash de contraseña |
| first_name | VARCHAR(255) | SÍ | Nombre |
| last_name | VARCHAR(255) | SÍ | Apellidos |
| email | VARCHAR(255) | NO | Email (único) |
| image_url | TEXT | SÍ | Avatar / imagen de perfil |
| created_at | TIMESTAMPTZ | NO | Fecha de creación |
| updated_at | TIMESTAMPTZ | NO | Fecha de última modificación |

**Notas técnicas**
- `login` y `email` son únicos.
- `updated_at` se actualiza automáticamente mediante trigger.
- La contraseña se almacena siempre como **hash**.

---

### 3.2 `project`

Representa proyectos dentro del sistema.

| Columna | Tipo | Nulo | Descripción |
|------|------|------|-------------|
| id | BIGINT | NO | Identificador único |
| name | VARCHAR(255) | NO | Nombre del proyecto (único) |
| description | TEXT | SÍ | Descripción funcional |
| logo_url | TEXT | SÍ | Logo o imagen |
| status | VARCHAR(50) | NO | Estado del proyecto |
| created_at | TIMESTAMPTZ | NO | Fecha de creación |
| updated_at | TIMESTAMPTZ | NO | Fecha de última modificación |

**Estados permitidos**
- `ACTIVE`
- `PAUSED`
- `ARCHIVED`

Controlados mediante `CHECK`.

---

### 3.3 `role`

Catálogo de roles del sistema.

| Columna | Tipo | Nulo | Descripción |
|------|------|------|-------------|
| id | BIGINT | NO | Identificador único |
| role_name | VARCHAR(255) | NO | Nombre del rol (único) |

**Roles típicos**
- ADMIN
- MANAGER
- MEMBER
- VIEWER

---

### 3.4 `cocoUser_project_role`

Tabla puente que implementa **RBAC por proyecto**.

| Columna | Tipo | Nulo | Descripción |
|------|------|------|-------------|
| user_id | BIGINT | NO | Usuario |
| project_id | BIGINT | NO | Proyecto |
| role_id | BIGINT | NO | Rol asignado |

**Claves**
- **PK**: `(user_id, project_id)`
- Un usuario tiene **exactamente un rol por proyecto**

**Reglas de borrado**
- Usuario eliminado → relaciones eliminadas (`CASCADE`)
- Proyecto eliminado → relaciones eliminadas (`CASCADE`)
- Rol eliminado → relaciones eliminadas (`CASCADE`)

---

### 3.5 `task_status`

Catálogo de estados de tareas (configurable).

| Columna | Tipo | Nulo | Descripción |
|------|------|------|-------------|
| status | VARCHAR(50) | NO | Identificador del estado |
| display_name | VARCHAR(100) | NO | Nombre visible |
| color_hex | CHAR(7) | NO | Color en formato HEX |
| sort_order | SMALLINT | NO | Orden de visualización |
| is_terminal | BOOLEAN | NO | Indica estado final |

**Ejemplos**
- TODO → Pendiente
- IN_PROGRESS → En progreso
- DONE → Hecho
- BLOCKED → Bloqueado

---

### 3.6 `tasks`

Tareas pertenecientes a proyectos.

| Columna | Tipo | Nulo | Descripción |
|------|------|------|-------------|
| id | BIGINT | NO | Identificador único |
| project_id | BIGINT | NO | Proyecto |
| title | VARCHAR(255) | NO | Título |
| description | TEXT | SÍ | Descripción |
| status | VARCHAR(50) | NO | Estado de la tarea |
| assigned_to | BIGINT | SÍ | Usuario asignado |
| due_date | DATE | SÍ | Fecha límite |
| created_at | TIMESTAMPTZ | NO | Fecha de creación |
| updated_at | TIMESTAMPTZ | NO | Fecha de última modificación |

**Relaciones**
- `project_id` → `project(id)` (`CASCADE`)
- `assigned_to` → `cocoUser(id)` (`SET NULL`)
- `status` → `task_status(status)` (`RESTRICT`)

---

## 4. Triggers y funciones

### `set_updated_at()`

Función reutilizable para mantener actualizado el campo `updated_at`.

Se ejecuta automáticamente en:
- `cocoUser`
- `project`
- `tasks`

Antes de cada `UPDATE`.

---

## 5. Reglas de integridad y diseño

- No existen valores mágicos: los estados de tareas están normalizados
- El control de acceso es **por proyecto**, no global
- No se permiten tareas con estados inexistentes
- El modelo soporta ejecución repetida de seeds sin duplicados
- Preparado para auditoría y extensiones futuras

---

## 6. Posibles extensiones futuras

- Historial de cambios de tareas (auditoría)
- Comentarios por tarea
- Etiquetas (labels)
- Asignación múltiple de usuarios a tareas
- Soft delete (`deleted_at`)
- Organización / tenant

