CREATE TABLE user_codes (
    id VARCHAR(36) PRIMARY KEY NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    code VARCHAR(6) UNIQUE NOT NULL,
    code_type VARCHAR(20) NOT NULL,
    expires_in TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL
);
