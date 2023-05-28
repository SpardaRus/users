--liquibase formatted sql

--changeset yasakovaa:TASK-4
--comment Создание тестовых пользователей

-- Создание пользователя без подписчиков
insert into users_scheme.users (first_name, last_name, email, phone, deleted, create_time, last_modify_time)
values ('Илья', 'Журавлев', 'ilya@yandex.ru', '+79271408413', false, '2023-05-20 00:00:00', '2023-05-20 00:00:00');

-- Создание подписчика Никита
insert into users_scheme.users (id, first_name, last_name, email, phone, deleted, create_time, last_modify_time)
values ('202572d2-f261-11ed-a05b-0242ac120003', 'Никита', 'Подписывающий', 'nikita@yandex.ru', '+79271408414', false,
        '2023-05-20 00:00:00', '2023-05-20 00:00:00');

-- Создание пользователя с подписчиком Рома
insert into users_scheme.users (id, first_name, last_name, email, phone, deleted, create_time, last_modify_time)
values ('a74a7726-f261-11ed-a05b-0242ac120003', 'Рома', 'Сафронов', 'roma@yandex.ru', '+79271408415', false,
        '2023-05-20 00:00:00', '2023-05-20 00:00:00');

-- Создание пользователя с подписчиком Вася
insert into users_scheme.users (id, first_name, last_name, email, phone, deleted, create_time, last_modify_time)
values ('291253a6-f261-11ed-a05b-0242ac120003', 'Вася', 'Уважаемый', 'vasya@yandex.ru', '+79271408416', false,
        '2023-05-20 00:00:00', '2023-05-20 00:00:00');