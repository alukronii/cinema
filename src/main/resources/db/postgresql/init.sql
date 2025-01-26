-- X Этап №1 Проектирование схемы базы данных. Нам необходимо хранить данные о показываемых фильмах, сеансах, проданных билетах. Для этого создадим базу данных и схему в бд:
-- X 1. Создать базу данных cinema.
-- X Внимание, для автоматической генерации id, воспользуйтесь типом данных serial вместо int. Serial генерирует новое значение при каждой вставке строки.
-- X 2. Создать сущность фильм (с характеристиками): уникальный идентификатор, наименование фильма, описание фильма.
-- x 3. В нашем кинотеатре только один зал, поэтому создадим схему рассадки.
-- x Создать сущность место: уникальный идентификатор, номер места (например "А1")
-- x 4. Создать сущность сеанс: уникальный идентификатор, идентификатор фильма(связь), время, цена.
-- x 5. Создать сущность билет: уникальный идентификатор, идентификатор места (связь), идентификатор сеанса (связь), куплен или нет.
-- Графическое отображение того, что должно получиться здесь: https://drawsql.app/teams/vtv-1/diagrams/cinema
-- 6. Создать репозиторий и положить скрипты в файл init.sql.
-- 7. Отправить код на Github.
-- 8. Запустить создание таблиц в базе данных.


CREATE TABLE movie (
	id SERIAL PRIMARY KEY,
	movie_name VARCHAR(255),
	movie_description VARCHAR(255)
);

CREATE TABLE place (
	id SERIAL PRIMARY KEY,
	place_number VARCHAR(4)
);

CREATE TABLE session (
	id SERIAL PRIMARY KEY,
	movie_id INT REFERENCES movie(id),
	timestamp TIMESTAMP,
	ticket_price NUMERIC(5,2)
);

CREATE TABLE ticket (
	id SERIAL PRIMARY KEY,
	place_id INT REFERENCES place(id),
	session_id INT REFERENCES session(id),
	is_sold BOOLEAN
);