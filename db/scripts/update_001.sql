CREATE TABLE IF NOT EXISTS engine(
    id SERIAL PRIMARY KEY,
    name TEXT
);

INSERT INTO engine(name) VALUES ('V4');
INSERT INTO engine(name) VALUES ('V6');
INSERT INTO engine(name) VALUES ('V8');
INSERT INTO engine(name) VALUES ('V12');

CREATE TABLE IF NOT EXISTS driver(
    id SERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS category(
    id SERIAL PRIMARY KEY,
    name TEXT
);

INSERT INTO category(name) VALUES ('Легковые');
INSERT INTO category(name) VALUES ('Коммерческие');
INSERT INTO category(name) VALUES ('Спец. техника');

CREATE TABLE IF NOT EXISTS body(
    id SERIAL PRIMARY KEY,
    type TEXT,
    category_id INT NOT NULL REFERENCES category(id)
);

INSERT INTO body(type, category_id) VALUES ('Седан', 1);
INSERT INTO body(type, category_id) VALUES ('Внедорожник', 1);
INSERT INTO body(type, category_id) VALUES ('Хэтчбек', 1);
INSERT INTO body(type, category_id) VALUES ('Лифтбек', 1);
INSERT INTO body(type, category_id) VALUES ('Универсал', 1);
INSERT INTO body(type, category_id) VALUES ('Минивэн', 1);
INSERT INTO body(type, category_id) VALUES ('Купе', 1);
INSERT INTO body(type, category_id) VALUES ('Пикап', 1);
INSERT INTO body(type, category_id) VALUES ('Кабриолет', 1);
INSERT INTO body(type, category_id) VALUES ('Фургон', 1);
INSERT INTO body(type, category_id) VALUES ('Легкие коммерческие', 1);
INSERT INTO body(type, category_id) VALUES ('Грузовики', 2);
INSERT INTO body(type, category_id) VALUES ('Седельные тягачи', 2);
INSERT INTO body(type, category_id) VALUES ('Автобусы', 2);
INSERT INTO body(type, category_id) VALUES ('Коммунальная', 3);
INSERT INTO body(type, category_id) VALUES ('Бульдозеры', 3);
INSERT INTO body(type, category_id) VALUES ('Экскаваторы', 3);

CREATE TABLE IF NOT EXISTS brand(
    id SERIAL PRIMARY KEY,
    name TEXT
);

INSERT INTO brand(name) VALUES ('BMW');
INSERT INTO brand(name) VALUES ('Audi');
INSERT INTO brand(name) VALUES ('Ford');
INSERT INTO brand(name) VALUES ('Hyundai');

CREATE TABLE IF NOT EXISTS model(
    id SERIAL PRIMARY KEY,
    name TEXT,
    brand_id INT NOT NULL REFERENCES brand(id)
);

INSERT INTO model(name, brand_id) VALUES ('M3', 1);
INSERT INTO model(name, brand_id) VALUES ('X5', 1);
INSERT INTO model(name, brand_id) VALUES ('7 Series', 1);
INSERT INTO model(name, brand_id) VALUES ('X6', 1);
INSERT INTO model(name, brand_id) VALUES ('80', 2);
INSERT INTO model(name, brand_id) VALUES ('100', 2);
INSERT INTO model(name, brand_id) VALUES ('A6', 2);
INSERT INTO model(name, brand_id) VALUES ('A8', 2);
INSERT INTO model(name, brand_id) VALUES ('Mustang', 3);
INSERT INTO model(name, brand_id) VALUES ('Transit', 3);
INSERT INTO model(name, brand_id) VALUES ('R160LC', 4);
INSERT INTO model(name, brand_id) VALUES ('R210LC', 4);

CREATE TABLE IF NOT EXISTS users(
    id SERIAL PRIMARY KEY,
    name TEXT,
    email VARCHAR(255) UNIQUE,
    password TEXT
);

CREATE TABLE IF NOT EXISTS car(
    id SERIAL PRIMARY KEY,
    engine_id INT NOT NULL REFERENCES engine(id),
    brand_id INT NOT NULL REFERENCES brand(id),
    model_id INT NOT NULL REFERENCES model(id),
    body_id INT NOT NULL REFERENCES body(id),
    category_id INT NOT NULL REFERENCES category(id)
);

CREATE TABLE IF NOT EXISTS history_owner(
    id SERIAL PRIMARY KEY,
    driver_id INT NOT NULL REFERENCES driver(id),
    car_id INT NOT NULL REFERENCES car(id)
);

CREATE TABLE IF NOT EXISTS ad(
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    car_id INT NOT NULL REFERENCES car(id),
    photo BYTEA,
    sold BOOLEAN,
    newCar BOOLEAN,
    user_id INT NOT NULL REFERENCES users(id),
    type TEXT
);