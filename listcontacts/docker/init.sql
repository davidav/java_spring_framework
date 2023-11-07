CREATE SCHEMA IF NOT EXISTS listcontacts_schema;

CREATE TABLE IF NOT EXISTS listcontacts_schema.contact
(
    id BIGINT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone BIGINT NOT NULL
)