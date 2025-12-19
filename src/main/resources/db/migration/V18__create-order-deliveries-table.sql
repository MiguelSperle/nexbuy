CREATE TABLE order_deliveries (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    order_id VARCHAR(36) UNIQUE NOT NULL,
    address_line VARCHAR(100) NOT NULL,
    address_number VARCHAR(15) NOT NULL,
    zip_code VARCHAR(9) NOT NULL,
    neighborhood VARCHAR(40) NOT NULL,
    city VARCHAR(40) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    complement VARCHAR(100),
    created_at TIMESTAMP NOT NULL
);
