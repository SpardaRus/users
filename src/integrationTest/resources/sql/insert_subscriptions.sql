--liquibase formatted sql

--changeset yasakovaa:TASK-5
--comment Создание тестовых подписчиков

-- Подписываем на Васю Никиту
insert into subscriptions (id, user_id, user_subscriber_id, deleted, create_time, last_modify_time)
values ('7f4d218e-f062-4169-8b68-b28626ab9244', '291253a6-f261-11ed-a05b-0242ac120003',
        '202572d2-f261-11ed-a05b-0242ac120003', false, '2023-05-20 00:00:00',
        '2023-05-20 00:00:00');

-- Подписываем на Рому Васю
insert into subscriptions (id, user_id, user_subscriber_id, deleted, create_time, last_modify_time)
values ('15a47728-7776-4380-8786-d80c48f8184c', 'a74a7726-f261-11ed-a05b-0242ac120003',
        '291253a6-f261-11ed-a05b-0242ac120003', false, '2023-05-20 00:00:00',
        '2023-05-20 00:00:00');