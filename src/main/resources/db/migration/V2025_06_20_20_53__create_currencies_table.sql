CREATE TABLE IF NOT EXISTS currencies
(
    id         BIGSERIAL PRIMARY KEY,
    name       TEXT                     NOT NULL,
    code       TEXT                     NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
    CONSTRAINT unq_currencies_code UNIQUE (code)
);
