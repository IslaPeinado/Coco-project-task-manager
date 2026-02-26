-- ============================================================
-- COCO - PostgreSQL
-- ============================================================


-- Roles base
INSERT INTO roles (role_name) VALUES
('ADMIN'),
('MEMBER'),
('VIEWER')
ON CONFLICT (role_name) DO NOTHING;

-- Estados base con colores
INSERT INTO tarea_estados (status, display_name, color_hex, sort_order, is_terminal) VALUES
('TODO',        'Pendiente',    '#6B7280', 10, FALSE),
('IN_PROGRESS', 'En progreso',  '#8B4513', 20, FALSE),
('DONE',        'Hecho',        '#10B981', 30, TRUE)
ON CONFLICT (status) DO NOTHING;



-- Usuarios 
INSERT INTO usuarios (username, name, lastname, email, password, profile_photo_url)
VALUES
('ana.lopez',  'Ana',  'López',   'ana.lopez@coco.local',  'CHANGE_ME_HASHED', ''),
('bruno.garcia','Bruno','García',  'bruno.garcia@coco.local','CHANGE_ME_HASHED',''),
('carla.martin','Carla','Martín',  'carla.martin@coco.local','CHANGE_ME_HASHED',''),
('david.ruiz','David','Ruiz','david.ruiz@coco.local','CHANGE_ME_HASHED','')
ON CONFLICT (username) DO NOTHING;


-- Proyectos 
INSERT INTO proyectos (name, description, logo_url)
SELECT
  'COCO - Gestión Interna',
  'Proyecto corporativo para organizar trabajo interno: equipos, tableros Kanban, asignación y auditoría.',
  ''
WHERE NOT EXISTS (SELECT 1 FROM proyectos WHERE name = 'COCO - Gestión Interna');

INSERT INTO proyectos (name, description, logo_url)
SELECT
  'COCO - Portal Cliente',
  'Espacio orientado a stakeholders externos: visibilidad controlada, reporting y seguimiento.',
  ''
WHERE NOT EXISTS (SELECT 1 FROM proyectos WHERE name = 'COCO - Portal Cliente');

INSERT INTO proyectos (name, description, logo_url)
SELECT
  'COCO - Reingeniería Seguridad',
  'Hardening y gobierno de acceso: roles por proyecto, JWT, trazabilidad y políticas.',
  ''
WHERE NOT EXISTS (SELECT 1 FROM proyectos WHERE name = 'COCO - Reingeniería Seguridad');


-- Roles por proyecto
-- Proyecto 1: ADMIN = Ana
INSERT INTO usuarios_proyectos_roles (user_id, project_id, role_id)
SELECT u.id, p.id, r.id
FROM usuarios u, proyectos p, roles r
WHERE u.username = 'ana.lopez'
  AND p.name = 'COCO - Gestión Interna'
  AND r.role_name = 'ADMIN'
ON CONFLICT DO NOTHING;

-- Proyecto 2: ADMIN = Bruno
INSERT INTO usuarios_proyectos_roles (user_id, project_id, role_id)
SELECT u.id, p.id, r.id
FROM usuarios u, proyectos p, roles r
WHERE u.username = 'bruno.garcia'
  AND p.name = 'COCO - Portal Cliente'
  AND r.role_name = 'ADMIN'
ON CONFLICT DO NOTHING;

-- Proyecto 3: ADMIN = Carla
INSERT INTO usuarios_proyectos_roles (user_id, project_id, role_id)
SELECT u.id, p.id, r.id
FROM usuarios u, proyectos p, roles r
WHERE u.username = 'carla.martin'
  AND p.name = 'COCO - Reingeniería Seguridad'
  AND r.role_name = 'ADMIN'
ON CONFLICT DO NOTHING;

-- Gestión Interna: Bruno MEMBER, David VIEWER
INSERT INTO usuarios_proyectos_roles (user_id, project_id, role_id)
SELECT u.id, p.id, r.id
FROM usuarios u, proyectos p, roles r
WHERE u.username = 'bruno.garcia'
  AND p.name = 'COCO - Gestión Interna'
  AND r.role_name = 'MEMBER'
ON CONFLICT DO NOTHING;

INSERT INTO usuarios_proyectos_roles (user_id, project_id, role_id)
SELECT u.id, p.id, r.id
FROM usuarios u, proyectos p, roles r
WHERE u.username = 'david.ruiz'
  AND p.name = 'COCO - Gestión Interna'
  AND r.role_name = 'VIEWER'
ON CONFLICT DO NOTHING;

-- Portal Cliente: Carla MEMBER, Ana VIEWER
INSERT INTO usuarios_proyectos_roles (user_id, project_id, role_id)
SELECT u.id, p.id, r.id
FROM usuarios u, proyectos p, roles r
WHERE u.username = 'carla.martin'
  AND p.name = 'COCO - Portal Cliente'
  AND r.role_name = 'MEMBER'
ON CONFLICT DO NOTHING;

INSERT INTO usuarios_proyectos_roles (user_id, project_id, role_id)
SELECT u.id, p.id, r.id
FROM usuarios u, proyectos p, roles r
WHERE u.username = 'ana.lopez'
  AND p.name = 'COCO - Portal Cliente'
  AND r.role_name = 'VIEWER'
ON CONFLICT DO NOTHING;

-- Reingeniería Seguridad: David MEMBER, Bruno VIEWER
INSERT INTO usuarios_proyectos_roles (user_id, project_id, role_id)
SELECT u.id, p.id, r.id
FROM usuarios u, proyectos p, roles r
WHERE u.username = 'david.ruiz'
  AND p.name = 'COCO - Reingeniería Seguridad'
  AND r.role_name = 'MEMBER'
ON CONFLICT DO NOTHING;

INSERT INTO usuarios_proyectos_roles (user_id, project_id, role_id)
SELECT u.id, p.id, r.id
FROM usuarios u, proyectos p, roles r
WHERE u.username = 'bruno.garcia'
  AND p.name = 'COCO - Reingeniería Seguridad'
  AND r.role_name = 'VIEWER'
ON CONFLICT DO NOTHING;


-- ------------------------
-- Tareas por proyecto 
-- ------------------------

-- === Proyecto 1: COCO - Gestión Interna (5 tareas)
INSERT INTO tareas (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM proyectos WHERE name='COCO - Gestión Interna'),
  'Definir backlog inicial',
  'Recopilar requisitos y definir épicas: proyectos, tareas, roles por proyecto.',
  'TODO',
  (SELECT id FROM usuarios WHERE username='ana.admin'),
  CURRENT_DATE + 7
WHERE NOT EXISTS (
  SELECT 1 FROM tareas t
  WHERE t.project_id = (SELECT id FROM proyectos WHERE name='COCO - Gestión Interna')
    AND t.title = 'Definir backlog inicial'
);

INSERT INTO tareas (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM proyectos WHERE name='COCO - Gestión Interna'),
  'Modelar entidades JPA (User/Project/Task)',
  'Crear entidades con relaciones y constraints coherentes para PostgreSQL.',
  'IN_PROGRESS',
  (SELECT id FROM usuarios WHERE username='bruno.admin'),
  CURRENT_DATE + 10
WHERE NOT EXISTS (
  SELECT 1 FROM tareas t
  WHERE t.project_id = (SELECT id FROM proyectos WHERE name='COCO - Gestión Interna')
    AND t.title = 'Modelar entidades JPA (User/Project/Task)'
);

INSERT INTO tareas (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM proyectos WHERE name='COCO - Gestión Interna'),
  'Diseñar tablero Kanban en Angular',
  'Componentes reutilizables + pipes + estados con color desde backend.',
  'TODO',
  (SELECT id FROM usuarios WHERE username='david.member'),
  CURRENT_DATE + 14
WHERE NOT EXISTS (
  SELECT 1 FROM tareas t
  WHERE t.project_id = (SELECT id FROM proyectos WHERE name='COCO - Gestión Interna')
    AND t.title = 'Diseñar tablero Kanban en Angular'
);

INSERT INTO tareas (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM proyectos WHERE name='COCO - Gestión Interna'),
  'Implementar endpoints REST (Projects/Tasks)',
  'CRUD con paginación, validación y OpenAPI/Swagger.',
  'IN_PROGRESS',
  (SELECT id FROM usuarios WHERE username='ana.admin'),
  CURRENT_DATE + 12
WHERE NOT EXISTS (
  SELECT 1 FROM tareas t
  WHERE t.project_id = (SELECT id FROM proyectos WHERE name='COCO - Gestión Interna')
    AND t.title = 'Implementar endpoints REST (Projects/Tasks)'
);

INSERT INTO tareas (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM proyectos WHERE name='COCO - Gestión Interna'),
  'Configurar CI básico',
  'Pipeline: build + tests + checks de estilo (enfoque mantenible).',
  'DONE',
  (SELECT id FROM usuarios WHERE username='bruno.admin'),
  CURRENT_DATE - 1
WHERE NOT EXISTS (
  SELECT 1 FROM tareas t
  WHERE t.project_id = (SELECT id FROM proyectos WHERE name='COCO - Gestión Interna')
    AND t.title = 'Configurar CI básico'
);


-- === Proyecto 2: COCO - Portal Cliente (5 tareas)
INSERT INTO tareas (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM proyectos WHERE name='COCO - Portal Cliente'),
  'Definir permisos de visibilidad',
  'Reglas de acceso: qué ve un VIEWER vs MEMBER vs ADMIN en el portal.',
  'TODO',
  (SELECT id FROM usuarios WHERE username='bruno.admin'),
  CURRENT_DATE + 8
WHERE NOT EXISTS (
  SELECT 1 FROM tareas t
  WHERE t.project_id = (SELECT id FROM proyectos WHERE name='COCO - Portal Cliente')
    AND t.title = 'Definir permisos de visibilidad'
);

INSERT INTO tareas (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM proyectos WHERE name='COCO - Portal Cliente'),
  'UI de listado de proyectos',
  'Cards con logo, métricas y navegación; modo dark/light.',
  'IN_PROGRESS',
  (SELECT id FROM usuarios WHERE username='carla.admin'),
  CURRENT_DATE + 9
WHERE NOT EXISTS (
  SELECT 1 FROM tareas t
  WHERE t.project_id = (SELECT id FROM proyectos WHERE name='COCO - Portal Cliente')
    AND t.title = 'UI de listado de proyectos'
);

INSERT INTO tareas (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM proyectos WHERE name='COCO - Portal Cliente'),
  'Implementar reporting básico',
  'Endpoint agregado: tareas por estado y vencimientos próximos.',
  'TODO',
  (SELECT id FROM usuarios WHERE username='ana.admin'),
  CURRENT_DATE + 15
WHERE NOT EXISTS (
  SELECT 1 FROM tareas t
  WHERE t.project_id = (SELECT id FROM proyectos WHERE name='COCO - Portal Cliente')
    AND t.title = 'Implementar reporting básico'
);

INSERT INTO tareas (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM proyectos WHERE name='COCO - Portal Cliente'),
  'Definir contrato OpenAPI para portal',
  'Documentación clara: DTOs, códigos de error, ejemplos.',
  'DONE',
  (SELECT id FROM usuarios WHERE username='bruno.admin'),
  CURRENT_DATE - 2
WHERE NOT EXISTS (
  SELECT 1 FROM tareas t
  WHERE t.project_id = (SELECT id FROM proyectos WHERE name='COCO - Portal Cliente')
    AND t.title = 'Definir contrato OpenAPI para portal'
);

INSERT INTO tareas (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM proyectos WHERE name='COCO - Portal Cliente'),
  'Feedback loop con stakeholders',
  'Iteración rápida: ajustar UI/UX y permisos según casos reales.',
  'IN_PROGRESS',
  (SELECT id FROM usuarios WHERE username='david.member'),
  CURRENT_DATE + 6
WHERE NOT EXISTS (
  SELECT 1 FROM tareas t
  WHERE t.project_id = (SELECT id FROM proyectos WHERE name='COCO - Portal Cliente')
    AND t.title = 'Feedback loop con stakeholders'
);


-- === Proyecto 3: COCO - Reingeniería Seguridad (5 tareas)
INSERT INTO tareas (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM proyectos WHERE name='COCO - Reingeniería Seguridad'),
  'Diseñar RBAC por proyecto',
  'Asegurar que roles se asignan por proyecto y se validan en cada endpoint.',
  'IN_PROGRESS',
  (SELECT id FROM usuarios WHERE username='carla.admin'),
  CURRENT_DATE + 5
WHERE NOT EXISTS (
  SELECT 1 FROM tareas t
  WHERE t.project_id = (SELECT id FROM proyectos WHERE name='COCO - Reingeniería Seguridad')
    AND t.title = 'Diseñar RBAC por proyecto'
);

INSERT INTO tareas (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM proyectos WHERE name='COCO - Reingeniería Seguridad'),
  'JWT + refresh strategy',
  'Definir expiración, refresh y revocación (según tu enfoque).',
  'TODO',
  (SELECT id FROM usuarios WHERE username='bruno.admin'),
  CURRENT_DATE + 11
WHERE NOT EXISTS (
  SELECT 1 FROM tareas t
  WHERE t.project_id = (SELECT id FROM proyectos WHERE name='COCO - Reingeniería Seguridad')
    AND t.title = 'JWT + refresh strategy'
);

INSERT INTO tareas (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM proyectos WHERE name='COCO - Reingeniería Seguridad'),
  'Auditoría de cambios',
  'Registrar quién cambia estado/asignación y cuándo (base para compliance).',
  'TODO',
  (SELECT id FROM usuarios WHERE username='david.member'),
  CURRENT_DATE + 20
WHERE NOT EXISTS (
  SELECT 1 FROM tareas t
  WHERE t.project_id = (SELECT id FROM proyectos WHERE name='COCO - Reingeniería Seguridad')
    AND t.title = 'Auditoría de cambios'
);

INSERT INTO tareas (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM proyectos WHERE name='COCO - Reingeniería Seguridad'),
  'Pruebas de seguridad (smoke)',
  'Tests básicos: autorización por rol, acceso denegado y casos borde.',
  'IN_PROGRESS',
  (SELECT id FROM usuarios WHERE username='carla.admin'),
  CURRENT_DATE + 13
WHERE NOT EXISTS (
  SELECT 1 FROM tareas t
  WHERE t.project_id = (SELECT id FROM proyectos WHERE name='COCO - Reingeniería Seguridad')
    AND t.title = 'Pruebas de seguridad (smoke)'
);

INSERT INTO tareas (project_id, title, description, status, assigned_to, due_date)
SELECT
  (SELECT id FROM proyectos WHERE name='COCO - Reingeniería Seguridad'),
  'Documentar amenazas y mitigaciones',
  'Mini threat-model: auth, roles, inyección, logging y hardening.',
  'DONE',
  (SELECT id FROM usuarios WHERE username='ana.admin'),
  CURRENT_DATE - 3
WHERE NOT EXISTS (
  SELECT 1 FROM tareas t
  WHERE t.project_id = (SELECT id FROM proyectos WHERE name='COCO - Reingeniería Seguridad')
    AND t.title = 'Documentar amenazas y mitigaciones'
);