-- Canonical project roles used by ProjectAuthorizationService/ProjectPolicy.
INSERT INTO role (role_name)
SELECT 'OWNER'
WHERE NOT EXISTS (SELECT 1 FROM role WHERE role_name = 'OWNER');

INSERT INTO role (role_name)
SELECT 'MANAGER'
WHERE NOT EXISTS (SELECT 1 FROM role WHERE role_name = 'MANAGER');

INSERT INTO role (role_name)
SELECT 'MEMBER'
WHERE NOT EXISTS (SELECT 1 FROM role WHERE role_name = 'MEMBER');

INSERT INTO role (role_name)
SELECT 'VIEWER'
WHERE NOT EXISTS (SELECT 1 FROM role WHERE role_name = 'VIEWER');

-- Legacy normalization: ADMIN is now OWNER for project-level RBAC.
UPDATE cocouser_project_role
SET role_id = (SELECT id FROM role WHERE role_name = 'OWNER')
WHERE role_id = (SELECT id FROM role WHERE role_name = 'ADMIN')
  AND EXISTS (SELECT 1 FROM role WHERE role_name = 'OWNER')
  AND EXISTS (SELECT 1 FROM role WHERE role_name = 'ADMIN');

DELETE FROM role
WHERE role_name = 'ADMIN'
  AND EXISTS (SELECT 1 FROM role WHERE role_name = 'OWNER');
