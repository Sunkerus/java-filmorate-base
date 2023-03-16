DROP TABLE IF EXISTS film_genre CASCADE;
DROP TABLE IF EXISTS subscribes CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS films CASCADE;
DROP TABLE IF EXISTS mpa CASCADE;
DROP TABLE IF EXISTS genre CASCADE;
DROP TABLE IF EXISTS rating CASCADE;
DROP TABLE IF EXISTS like_to_film CASCADE;


CREATE TABLE IF NOT EXISTS mpa
(
    id   int PRIMARY KEY AUTO_INCREMENT,
    name varchar(300),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS genre
(
    id   int PRIMARY KEY AUTO_INCREMENT,
    name varchar(300),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users
(
    id       int PRIMARY KEY AUTO_INCREMENT,
    email    varchar(300),
    login    varchar(300),
    name     varchar(300),
    birthday date,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS subscribes
(
    author     int
        CONSTRAINT author_id_fk REFERENCES users (id),
    subscriber int
        CONSTRAINT subscriber_id_fk REFERENCES users (id),
    CONSTRAINT subscribe_id_fk UNIQUE (author, subscriber)
);


CREATE TABLE IF NOT EXISTS films
(
    id           int PRIMARY KEY AUTO_INCREMENT,
    name         varchar(300),
    mpa          int
        CONSTRAINT films_mpa_id_fk REFERENCES mpa (id),
    rate         int DEFAULT 0,
    description  varchar(300),
    release_date date,
    duration     int,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS film_genre
(
    film_id  int
        CONSTRAINT films_id_fk REFERENCES films (id),
    genre_id int
        CONSTRAINT genre_id_fk REFERENCES genre (id),
    CONSTRAINT unique_id_fk UNIQUE (film_id, genre_id)
);


CREATE TABLE IF NOT EXISTS like_to_film
(
    user_id int
        CONSTRAINT users_id_fk_rate REFERENCES users (id),
    film_id int
        CONSTRAINT films_id_fk_rate REFERENCES films (id),
    CONSTRAINT unique_id UNIQUE (user_id, film_id)
);
