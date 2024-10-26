--liquibase formatted sql

-- changeset name:1
CREATE TABLE users_tb (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL
);