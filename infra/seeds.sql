-- ============================================================
-- COCO - PostgreSQL - SEEDS
-- Compatible with tables: cocoUser, project, role, task_status, tasks, cocoUser_project_role
-- ============================================================

-- ------------------------
-- Roles base
-- ------------------------
INSERT INTO role (role_name) VALUES
('ADMIN'),
('MANAGER'),
('MEMBER'),
('VIEWER')
ON CONFLICT (role_name) DO NOTHING;

-- ------------------------
-- Estados base (contenido en español)
-- ------------------------
INSERT INTO task_status (status, display_name, color_hex, sort_order, is_terminal) VALUES
('TODO',        'Pendiente',    '#6B7280', 10, FALSE),
('IN_PROGRESS', 'En progreso',  '#8B4513', 20, FALSE),
('DONE',        'Hecho',        '#10B981', 30, TRUE),
('BLOCKED',     'Bloqueado',    '#EF4444', 25, FALSE)
ON CONFLICT (status) DO NOTHING;

-- ------------------------
-- Usuarios
-- ------------------------
INSERT INTO cocoUser (login, first_name, last_name, email, password, image_url)
VALUES
('ana.lopez',    'Ana',   'López',   'ana.lopez@coco.com',     'CHANGE_ME_HASHED', ''),
('bruno.garcia', 'Bruno', 'García',  'bruno.garcia@coco.com',  'CHANGE_ME_HASHED', ''),
('carla.martin', 'Carla', 'Martín',  'carla.martin@coco.com',  'CHANGE_ME_HASHED', ''),
('david.ruiz',   'David', 'Ruiz',    'david.ruiz@coco.com',    'CHANGE_ME_HASHED', '')
ON CONFLICT (login) DO NOTHING;

-- ------------------------
-- Proyectos
-- ------------------------
INSERT INTO project (name, description, logo_url)
SELECT
  'COCO - Gestión Interna',
  'Proyecto corporativo para organizar trabajo interno: equipos, tableros Kanban, asignación y auditoría.',
  ''
WHERE NOT EXISTS (SELECT 1 FROM project WHERE name = 'COCO - Gestión Interna');

INSERT INTO project (name, description, logo_url)
SELECT
  'COCO - Portal Cliente',
  'Espacio orientado a stakeholders externos: visibilidad controlada, reporting y seguimiento.',
  ''
WHERE NOT EXISTS (SELECT 1 FROM project WHERE name = 'COCO - Portal Cliente');

INSERT INTO project (name, description, logo_url)
SELECT
  'COCO - Reingeniería Seguridad',
  'Hardening y gobierno de acceso: roles por proyecto, JWT, trazabilidad y políticas.',
  ''
WHERE NOT EXISTS (SELECT 1 FROM project WHERE name = 'COCO - Reingeniería Seguridad');

-- ------------------------
-- Roles por proyecto (1 rol por usuario y proyecto)
-- PK: (user_id, project_id)
-- ------------------------

-- Proyecto 1: ADMIN = Ana
INSERT INTO cocoUser_project_role (user_id, project_id, role_id)
SELECT u.id, p.id, r.id
FROM cocoUser u
JOIN project p ON p.name = 'COCO - Gestión Interna'
JOIN role r ON r.role_name = 'ADMIN'
WHERE u.login = 'ana.lopez'
ON CONFLICT (user_id, project_id) DO UPDATE
SET role_id = EXCLUDED.role_id;

-- Proyecto 2: ADMIN = Bruno
INSERT INTO cocoUser_project_role (user_id, project_id, role_id)
SELECT u.id, p.id, r.id
FROM cocoUser u
JOIN project p ON p.name = 'COCO - Portal Cliente'
JOIN role r ON r.role_name = 'ADMIN'
WHERE u.login = 'bruno.garcia'
ON CONFLICT (user_id, project_id) DO UPDATE
SET role_id = EXCLUDED.role_id;

-- Proyecto 3: ADMIN = Carla
INSERT INTO cocoUser_project_role (user_id, project_id, role_id)
SELECT u.id, p.id, r.id
FROM cocoUser u
JOIN project p ON p.name = 'COCO - Reingeniería Seguridad'
JOIN role r ON r.role_name = 'ADMIN'
WHERE u.login = 'carla.martin'
ON CONFLICT (user_id, project_id) DO UPDATE
SET role_id = EXCLUDED.role_id;

-- Gestión Interna: Bruno MEMBER, David VIEWER
INSERT INTO cocoUser_project_role (user_id, project_id, role_id)
SELECT u.id, p.id, r.id
FROM cocoUser u
JOIN project p ON p.name = 'COCO - Gestión Interna'
JOIN role r ON r.role_name = 'MEMBER'
WHERE u.login = 'bruno.garcia'
ON CONFLICT (user_id, project_id) DO UPDATE
SET role_id = EXCLUDED.role_id;

INSERT INTO cocoUser_project_role (user_id, project_id, role_id)
SELECT u.id, p.id, r.id
FROM cocoUser u
JOIN project p ON p.name = 'COCO - Gestión Interna'
JOIN role r ON r.role_name = 'VIEWER'
WHERE u.login = 'david.ruiz'
ON CONFLICT (user_id, project_id) DO UPDATE
SET role_id = EXCLUDED.role_id;

-- Portal Cliente: Carla MEMBER, Ana VIEWER
INSERT INTO cocoUser_project_role (user_id, project_id, role_id)
SELECT u.id, p.id, r.id
FROM cocoUser u
JOIN project p ON p.name = 'COCO - Portal Cliente'
JOIN role r ON r.role_name = 'MEMBER'
WHERE u.login = 'carla.martin'
ON CONFLICT (user_id, project_id) DO UPDATE
SET role_id = EXCLUDED.role_id;

INSERT INTO cocoUser_project_role (user_id, project_id, role_id)
SELECT u.id, p.id, r.id
FROM cocoUser u
JOIN project p ON p.name = 'COCO - Portal Cliente'
JOIN role r ON r.role_name = 'VIEWER'
WHERE u.login = 'ana.lopez'
ON CONFLICT (user_id, project_id) DO UPDATE
SET role_id = EXCLUDED.role_id;

-- Reingeniería Seguridad: David MEMBER, Bruno VIEWER
INSERT INTO cocoUser_project_role (user_id, project_id, role_id)
SELECT u.id, p.id, r.id
FROM cocoUser u
JOIN project p ON p.name = 'COCO - Reingeniería Seguridad'
JOIN role r ON r.role_name = 'MEMBER'
WHERE u.login = 'david.ruiz'
ON CONFLICT (user_id, project_id) DO UPDATE
SET role_id = EXCLUDED.role_id;

INSERT INTO cocoUser_project_role (user_id, project_id, role_id)
SELECT u.id, p.id, r.id
FROM cocoUser u
JOIN project p ON p.name = 'COCO - Reingeniería Seguridad'
JOIN role r ON r.role_name = 'VIEWER'
WHERE u.login = 'bruno.garcia'
ON CONFLICT (user_id, project_id) DO UPDATE
SET role_id = EXCLUDED.role_id;

-- ------------------------
-- Tareas por proyecto
-- Tabla: tasks (project_id, title, description, status, assigned_to, due_date)
-- ------------------------

-- === Proyecto 1: COCO - Gestión Interna (5 tareas)
INSERT INTO tasks (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM project WHERE name='COCO - Gestión Interna'),
  'Definir backlog inicial',
  'Recopilar requisitos y definir épicas: proyectos, tareas, roles por proyecto.',
  'TODO',
  (SELECT id FROM cocoUser WHERE login='ana.lopez'),
  CURRENT_DATE + 7
WHERE NOT EXISTS (
  SELECT 1 FROM tasks t
  WHERE t.project_id = (SELECT id FROM project WHERE name='COCO - Gestión Interna')
    AND t.title = 'Definir backlog inicial'
);

INSERT INTO tasks (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM project WHERE name='COCO - Gestión Interna'),
  'Modelar entidades JPA (User/Project/Task)',
  'Crear entidades con relaciones y constraints coherentes para PostgreSQL.',
  'IN_PROGRESS',
  (SELECT id FROM cocoUser WHERE login='bruno.garcia'),
  CURRENT_DATE + 10
WHERE NOT EXISTS (
  SELECT 1 FROM tasks t
  WHERE t.project_id = (SELECT id FROM project WHERE name='COCO - Gestión Interna')
    AND t.title = 'Modelar entidades JPA (User/Project/Task)'
);

INSERT INTO tasks (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM project WHERE name='COCO - Gestión Interna'),
  'Diseñar tablero Kanban en Angular',
  'Componentes reutilizables + pipes + estados con color desde backend.',
  'TODO',
  (SELECT id FROM cocoUser WHERE login='david.ruiz'),
  CURRENT_DATE + 14
WHERE NOT EXISTS (
  SELECT 1 FROM tasks t
  WHERE t.project_id = (SELECT id FROM project WHERE name='COCO - Gestión Interna')
    AND t.title = 'Diseñar tablero Kanban en Angular'
);

INSERT INTO tasks (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM project WHERE name='COCO - Gestión Interna'),
  'Implementar endpoints REST (Projects/Tasks)',
  'CRUD con paginación, validación y OpenAPI/Swagger.',
  'IN_PROGRESS',
  (SELECT id FROM cocoUser WHERE login='ana.lopez'),
  CURRENT_DATE + 12
WHERE NOT EXISTS (
  SELECT 1 FROM tasks t
  WHERE t.project_id = (SELECT id FROM project WHERE name='COCO - Gestión Interna')
    AND t.title = 'Implementar endpoints REST (Projects/Tasks)'
);

INSERT INTO tasks (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM project WHERE name='COCO - Gestión Interna'),
  'Configurar CI básico',
  'Pipeline: build + tests + checks de estilo (enfoque mantenible).',
  'DONE',
  (SELECT id FROM cocoUser WHERE login='bruno.garcia'),
  CURRENT_DATE - 1
WHERE NOT EXISTS (
  SELECT 1 FROM tasks t
  WHERE t.project_id = (SELECT id FROM project WHERE name='COCO - Gestión Interna')
    AND t.title = 'Configurar CI básico'
);

-- === Proyecto 2: COCO - Portal Cliente (5 tareas)
INSERT INTO tasks (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM project WHERE name='COCO - Portal Cliente'),
  'Definir permisos de visibilidad',
  'Reglas de acceso: qué ve un VIEWER vs MEMBER vs ADMIN en el portal.',
  'TODO',
  (SELECT id FROM cocoUser WHERE login='bruno.garcia'),
  CURRENT_DATE + 8
WHERE NOT EXISTS (
  SELECT 1 FROM tasks t
  WHERE t.project_id = (SELECT id FROM project WHERE name='COCO - Portal Cliente')
    AND t.title = 'Definir permisos de visibilidad'
);

INSERT INTO tasks (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM project WHERE name='COCO - Portal Cliente'),
  'UI de listado de proyectos',
  'Cards con logo, métricas y navegación; modo dark/light.',
  'IN_PROGRESS',
  (SELECT id FROM cocoUser WHERE login='carla.martin'),
  CURRENT_DATE + 9
WHERE NOT EXISTS (
  SELECT 1 FROM tasks t
  WHERE t.project_id = (SELECT id FROM project WHERE name='COCO - Portal Cliente')
    AND t.title = 'UI de listado de proyectos'
);

INSERT INTO tasks (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM project WHERE name='COCO - Portal Cliente'),
  'Implementar reporting básico',
  'Endpoint agregado: tareas por estado y vencimientos próximos.',
  'TODO',
  (SELECT id FROM cocoUser WHERE login='ana.lopez'),
  CURRENT_DATE + 15
WHERE NOT EXISTS (
  SELECT 1 FROM tasks t
  WHERE t.project_id = (SELECT id FROM project WHERE name='COCO - Portal Cliente')
    AND t.title = 'Implementar reporting básico'
);

INSERT INTO tasks (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM project WHERE name='COCO - Portal Cliente'),
  'Definir contrato OpenAPI para portal',
  'Documentación clara: DTOs, códigos de error, ejemplos.',
  'DONE',
  (SELECT id FROM cocoUser WHERE login='bruno.garcia'),
  CURRENT_DATE - 2
WHERE NOT EXISTS (
  SELECT 1 FROM tasks t
  WHERE t.project_id = (SELECT id FROM project WHERE name='COCO - Portal Cliente')
    AND t.title = 'Definir contrato OpenAPI para portal'
);

INSERT INTO tasks (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM project WHERE name='COCO - Portal Cliente'),
  'Feedback loop con stakeholders',
  'Iteración rápida: ajustar UI/UX y permisos según casos reales.',
  'IN_PROGRESS',
  (SELECT id FROM cocoUser WHERE login='david.ruiz'),
  CURRENT_DATE + 6
WHERE NOT EXISTS (
  SELECT 1 FROM tasks t
  WHERE t.project_id = (SELECT id FROM project WHERE name='COCO - Portal Cliente')
    AND t.title = 'Feedback loop con stakeholders'
);

-- === Proyecto 3: COCO - Reingeniería Seguridad (5 tareas)
INSERT INTO tasks (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM project WHERE name='COCO - Reingeniería Seguridad'),
  'Diseñar RBAC por proyecto',
  'Asegurar que roles se asignan por proyecto y se validan en cada endpoint.',
  'IN_PROGRESS',
  (SELECT id FROM cocoUser WHERE login='carla.martin'),
  CURRENT_DATE + 5
WHERE NOT EXISTS (
  SELECT 1 FROM tasks t
  WHERE t.project_id = (SELECT id FROM project WHERE name='COCO - Reingeniería Seguridad')
    AND t.title = 'Diseñar RBAC por proyecto'
);

INSERT INTO tasks (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM project WHERE name='COCO - Reingeniería Seguridad'),
  'JWT + refresh strategy',
  'Definir expiración, refresh y revocación (según tu enfoque).',
  'TODO',
  (SELECT id FROM cocoUser WHERE login='bruno.garcia'),
  CURRENT_DATE + 11
WHERE NOT EXISTS (
  SELECT 1 FROM tasks t
  WHERE t.project_id = (SELECT id FROM project WHERE name='COCO - Reingeniería Seguridad')
    AND t.title = 'JWT + refresh strategy'
);

INSERT INTO tasks (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM project WHERE name='COCO - Reingeniería Seguridad'),
  'Auditoría de cambios',
  'Registrar quién cambia estado/asignación y cuándo (base para compliance).',
  'TODO',
  (SELECT id FROM cocoUser WHERE login='david.ruiz'),
  CURRENT_DATE + 20
WHERE NOT EXISTS (
  SELECT 1 FROM tasks t
  WHERE t.project_id = (SELECT id FROM project WHERE name='COCO - Reingeniería Seguridad')
    AND t.title = 'Auditoría de cambios'
);

INSERT INTO tasks (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM project WHERE name='COCO - Reingeniería Seguridad'),
  'Pruebas de seguridad (smoke)',
  'Tests básicos: autorización por rol, acceso denegado y casos borde.',
  'IN_PROGRESS',
  (SELECT id FROM cocoUser WHERE login='carla.martin'),
  CURRENT_DATE + 13
WHERE NOT EXISTS (
  SELECT 1 FROM tasks t
  WHERE t.project_id = (SELECT id FROM project WHERE name='COCO - Reingeniería Seguridad')
    AND t.title = 'Pruebas de seguridad (smoke)'
);

INSERT INTO tasks (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM project WHERE name='COCO - Reingeniería Seguridad'),
  'Documentar amenazas y mitigaciones',
  'Mini threat-model: auth, roles, inyección, logging y hardening.',
  'DONE',
  (SELECT id FROM cocoUser WHERE login='ana.lopez'),
  CURRENT_DATE - 3
WHERE NOT EXISTS (
  SELECT 1 FROM tasks t
  WHERE t.project_id = (SELECT id FROM project WHERE name='COCO - Reingeniería Seguridad')
    AND t.title = 'Documentar amenazas y mitigaciones'
);
