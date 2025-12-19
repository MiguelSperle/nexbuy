CREATE TABLE payments (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    payment_method VARCHAR(20) NOT NULL,
    total_amount NUMERIC(19, 2) NOT NULL,
    order_id VARCHAR(36) UNIQUE NOT NULL,
    status VARCHAR(10) NOT NULL,
    created_at TIMESTAMP NOT NULL
);
