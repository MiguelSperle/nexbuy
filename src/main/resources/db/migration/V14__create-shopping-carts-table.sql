CREATE TABLE shopping_carts (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    user_id VARCHAR(36) UNIQUE NOT NULL,
    total_amount NUMERIC(19, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL
);
