DROP SCHEMA book_storage CASCADE;


CREATE DATABASE book_storage;

CREATE SCHEMA book_storage;

SET search_path TO book_storage;


CREATE TABLE author (
  id            BIGSERIAL PRIMARY KEY ,
  name          VARCHAR(50) NOT NULL,
  date_of_birth date        NOT NULL
);

CREATE TABLE genre (
  id   SERIAL PRIMARY KEY ,
  name VARCHAR(20) NOT NULL
);

CREATE TABLE book (
  id            BIGSERIAL PRIMARY KEY ,
  name          VARCHAR(50) NOT NULL,
  creation_year INTEGER     NOT NULL,
  genre_id      INTEGER     NOT NULL REFERENCES genre (id),
  author_id     BIGINT      NOT NULL REFERENCES author (id)
);

INSERT INTO author (name, date_of_birth) VALUES
  ('Agatha Christie', '1890-09-15'),
  ('Arthur Conan Doyle', '1859-05-22'),
  ('Frank Herbert', '1920-10-08'),
  ('George Orwell', '1903-06-25'),
  ('Shakespeare', '1564-04-01');

INSERT INTO genre (name) VALUES
  ('Science fiction'),
  ('Poetry'),
  ('Detective');

INSERT INTO book (name, creation_year, genre_id, author_id) VALUES
  ('Murder on The Orient Express', 1934, 3, 1),
  ('Mysterious Affair at Styles', 1920, 3, 1),
  ('Sherlock Holmes', 1892, 3, 2),
  ('Dune', 1965, 1, 3),
  ('1984', 1948, 1, 4),
  ('Romeo and Juliet', 1595, 2, 5),
  ('The Tragedy of Othello', 1604, 2, 5);
