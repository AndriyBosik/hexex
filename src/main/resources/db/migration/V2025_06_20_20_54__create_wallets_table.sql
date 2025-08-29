CREATE TABLE IF NOT EXISTS wallets
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT                   NOT NULL,
    currency_id BIGINT                   NOT NULL,
    total       BIGINT                   NOT NULL,
    available   BIGINT                   NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT (NOW() AT TIME ZONE 'UTC'),
    CONSTRAINT fk_wallets_user_id FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_wallets_currency_id FOREIGN KEY (currency_id) REFERENCES currencies (id),
    CONSTRAINT unq_wallets_user_id_currency_id UNIQUE (user_id, currency_id)
);
