CREATE TABLE USERS (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    status VARCHAR(10) NOT NULL,
    authorization_role VARCHAR(10) NOT NULL,
    person_type VARCHAR(15) NOT NULL,
    created_at TIMESTAMP NOT NULL
);
