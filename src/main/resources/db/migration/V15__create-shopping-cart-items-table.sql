CREATE TABLE shopping_cart_items (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    shopping_cart_id VARCHAR(36) NOT NULL,
    product_id VARCHAR(36) NOT NULL,
    quantity INTEGER NOT NULL,
    unit_price NUMERIC(19, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL
);
