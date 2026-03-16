-- Base task status catalog.
INSERT INTO task_status (status, display_name, color_hex, sort_order, is_terminal)
SELECT 'TODO', 'To Do', '#64748B', 10, FALSE
WHERE NOT EXISTS (SELECT 1 FROM task_status WHERE status = 'TODO');

INSERT INTO task_status (status, display_name, color_hex, sort_order, is_terminal)
SELECT 'IN_PROGRESS', 'In Progress', '#0EA5E9', 20, FALSE
WHERE NOT EXISTS (SELECT 1 FROM task_status WHERE status = 'IN_PROGRESS');

INSERT INTO task_status (status, display_name, color_hex, sort_order, is_terminal)
SELECT 'DONE', 'Done', '#22C55E', 30, TRUE
WHERE NOT EXISTS (SELECT 1 FROM task_status WHERE status = 'DONE');
