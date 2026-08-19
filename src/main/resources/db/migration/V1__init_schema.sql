-- gen_random_uuid() is built-in since PG 13, but pgcrypto keeps this
-- migration safe if the target ever runs an older PostgreSQL version.
CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE projects (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title       VARCHAR(150) NOT NULL,
    description TEXT NOT NULL,
    due_date    DATE NOT NULL,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE tasks (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    text        VARCHAR(500) NOT NULL,
    done        BOOLEAN NOT NULL DEFAULT FALSE,
    project_id  UUID NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_tasks_project_id ON tasks(project_id);
