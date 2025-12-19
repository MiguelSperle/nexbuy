CREATE TABLE inventory_movements (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    inventory_id VARCHAR(36) NOT NULL,
    sku VARCHAR(80) NOT NULL,
    quantity INTEGER NOT NULL,
    movement_type VARCHAR(3) NOT NULL,
    created_at TIMESTAMP NOT NULL
);
