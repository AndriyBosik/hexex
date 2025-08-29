CREATE TABLE IF NOT EXISTS lean_customers
(
    id          BIGSERIAL PRIMARY KEY,
    email       TEXT                     NOT NULL,
    customer_id TEXT                     NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC')
);
