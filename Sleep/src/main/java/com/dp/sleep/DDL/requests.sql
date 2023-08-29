select * from public.converted_data;

select min(sleep_duration) from converted_data order by id;

select login, first_name, id from users where
(id between 1 and 5 or id = 10 or first_name like '%eni%')
and id NOT IN (100,200)
order by first_name desc;

SELECT first_name, consume_id
FROM users
WHERE last_name IN (
    SELECT last_name
    FROM (
             SELECT last_name
             FROM users
             GROUP BY last_name
         ) AS results
    WHERE results.last_name like '%a%');

insert into input_data(id, humidity, temperature, is_deleted, date)
values
(5, 87, 23, false, to_timestamp('2023-05-04 14:30:27.001', 'YYYY-MM-DD HH24:MI:SS.FF'));

insert into input_data(id, humidity, temperature, is_deleted, date)
values
    (6, 87, 23, false, to_timestamp('2023-05-04 14:30:27.001', 'YYYY-MM-DD HH24:MI:SS.FF'));


