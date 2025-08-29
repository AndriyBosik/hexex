CREATE TABLE IF NOT EXISTS offline_payment_details
(
    id                  BIGSERIAL PRIMARY KEY,
    deposit_id          BIGINT                   NOT NULL,
    amount              BIGINT                   NOT NULL,
    currency            TEXT                     NOT NULL,
    status              TEXT                     NOT NULL,
    bank_name           TEXT                     NOT NULL,
    bank_account_number TEXT                     NOT NULL,
    created_at          TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
    updated_at          TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
    CONSTRAINT fk_offline_payment_details_deposit_id FOREIGN KEY (deposit_id) REFERENCES deposits (id)
);
