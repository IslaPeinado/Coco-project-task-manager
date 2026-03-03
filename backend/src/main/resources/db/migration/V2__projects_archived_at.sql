ALTER TABLE project
    ADD COLUMN IF NOT EXISTS archived_at TIMESTAMPTZ NULL;

CREATE INDEX IF NOT EXISTS idx_projects_status_archived_at
    ON project(status, archived_at);
