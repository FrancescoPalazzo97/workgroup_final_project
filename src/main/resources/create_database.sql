CREATE DATABASE IF NOT EXISTS masamune_ex;

USE masamune_ex;

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

CREATE TABLE IF NOT EXISTS borrowings (
    id INT NOT NULL AUTO_INCREMENT,
    book_id INT NOT NULL,
    user_id BIGINT NULL,
    borrowin_date DATE NOT NULL,
    retur_date DATE NULL,
    notes TEXT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_borrowings_books FOREIGN KEY (book_id) REFERENCES books(id),
    CONSTRAINT fk_borrowings_users FOREIGN KEY (user_id) REFERENCES users(id)
);
