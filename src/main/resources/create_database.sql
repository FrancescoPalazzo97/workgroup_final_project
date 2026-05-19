CREATE DATABASE IF NOT EXISTS masamune;

USE masamune;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_users_email (email)
);

CREATE TABLE IF NOT EXISTS books (
    id INT NOT NULL AUTO_INCREMENT,
    titolo VARCHAR(255) NOT NULL,
    autore VARCHAR(255) NOT NULL,
    anno_pubblicazione INT NOT NULL,
    disponibile BIT NOT NULL,
    PRIMARY KEY (id)
);
