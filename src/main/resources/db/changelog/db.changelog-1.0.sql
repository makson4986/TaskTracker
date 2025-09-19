--liquibase formatted sql

--changeset makson:1
CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    email    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(512)       NOT NULL
);
--rollback DROP TABLE users;

--changeset makson:2
CREATE TABLE IF NOT EXISTS tasks
(
    id     SERIAL PRIMARY KEY,
    title  VARCHAR(256) NOT NULL,
    text   TEXT         NOT NULL,
    owner  INT          NOT NULL,
    status VARCHAR(32)  NOT NULL DEFAULT 'NOT_DONE',
    performedAt   TIMESTAMP,
    FOREIGN KEY (owner) REFERENCES users (id)
);
--rollback DROP TABLE tasks;

