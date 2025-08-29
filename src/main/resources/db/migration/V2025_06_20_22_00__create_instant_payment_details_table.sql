CREATE TABLE IF NOT EXISTS instant_payment_details
(
    id         BIGSERIAL PRIMARY KEY,
    deposit_id BIGINT                   NOT NULL,
    intent_id  TEXT                     NOT NULL,
    payment_id TEXT,
    amount     BIGINT                   NOT NULL,
    status     TEXT                     NOT NULL,
    currency   TEXT                     NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
    CONSTRAINT fk_instant_payment_details_deposit_id FOREIGN KEY (deposit_id) REFERENCES deposits (id)
);
