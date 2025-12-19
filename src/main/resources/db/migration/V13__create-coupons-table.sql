CREATE TABLE coupons (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,
    percentage INTEGER NOT NULL,
    minimum_purchase_amount NUMERIC(19, 2) NOT NULL,
    is_active BOOLEAN NOT NULL,
    expires_in TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL
);
