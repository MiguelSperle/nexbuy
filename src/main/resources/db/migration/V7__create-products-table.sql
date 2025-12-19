CREATE TABLE products (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    name VARCHAR(150) NOT NULL,
    description TEXT NOT NULL,
    category_id VARCHAR(36) NOT NULL,
    price NUMERIC(19, 2) NOT NULL,
    sku VARCHAR(80) UNIQUE NOT NULL,
    brand_id VARCHAR(36) NOT NULL,
    color_id VARCHAR(36) NOT NULL,
    status VARCHAR(10) NOT NULL,
    weight INTEGER NOT NULL,
    height INTEGER NOT NULL,
    width INTEGER NOT NULL,
    length INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL
);
