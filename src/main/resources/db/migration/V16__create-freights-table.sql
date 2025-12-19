CREATE TABLE freights (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    order_id VARCHAR(36) UNIQUE NOT NULL,
    name VARCHAR(12) NOT NULL,
    company_name VARCHAR(20) NOT NULL,
    price NUMERIC(19, 2) NOT NULL,
    estimated_time INTEGER NOT NULL,
    min_time INTEGER NOT NULL,
    max_time INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL
);
