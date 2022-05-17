CREATE TABLE IF NOT EXISTS engine(
    id SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE IF NOT EXISTS driver(
    id SERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS body(
    id SERIAL PRIMARY KEY,
    type TEXT
);

CREATE TABLE IF NOT EXISTS brand(
    id SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE IF NOT EXISTS user(
    id SERIAL PRIMARY KEY,
    name TEXT,
    email VARCHAR(255) UNIQUE,
    password TEXT
);

CREATE TABLE IF NOT EXISTS car(
    id SERIAL PRIMARY KEY,
    engine_id INT NOT NULL REFERENCES engine(id),
    brand_id INT NOT NULL REFERENCES brand(id),
    body_id INT NOT NULL REFERENCES body(id)
);

CREATE TABLE IF NOT EXISTS history_owner(
    id SERIAL PRIMARY KEY,
    driver_id INT NOT NULL REFERENCES driver(id),
    car_id INT NOT NULL REFERENCES car(id)
);

CREATE TABLE IF NOT EXISTS post(
    id SERIAL PRIMARY KEY,
    description TEXT,
    car_id INT NOT NULL REFERENCES car(id),
    photo BYTEA,
    sold BOOLEAN,
    user_id INT NOT NULL REFERENCES user(id)
);