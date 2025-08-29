CREATE TABLE IF NOT EXISTS deposits
(
    id                 BIGSERIAL PRIMARY KEY,
    wallet_id          BIGINT                   NOT NULL,
    amount             BIGINT                   NOT NULL,
    transaction_status TEXT                     NOT NULL,
    created_at         TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
    updated_at         TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
    CONSTRAINT fk_deposits_wallet_id FOREIGN KEY (wallet_id) REFERENCES wallets (id)
);
