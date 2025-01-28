-- 0. Добавить 10 мест с помощью sql скрипта (т.е. запустить скрипт) и положить в скрипт fill-data.sql (папка resources).:
-- A1 -> A5
-- B1 -> B5

-- Сразу скажу подсмотрел эту штуку в https://dev.to/antjanus/using-postgres-for-loop-to-generate-data-3mm2
-- просто стало интересно если реально придется много разного вставлять, а оно повторяется, и тут нашел такое.
-- ТАм еще что-то про ARRAY[] и unnest было, но у меня оно не заработало, или я не понял как правильно.

INSERT INTO place (place_number)
SELECT 'A' || a.n
FROM generate_series(1,5) as a(n);

INSERT INTO place (place_number)
SELECT 'B' || a.n
FROM generate_series(1,5) as a(n);

SELECT *
FROM place;