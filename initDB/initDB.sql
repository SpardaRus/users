-- Создание схемы
create schema users_scheme;

-- Создание таблицы пользователей
create table IF NOT EXISTS users_scheme.users
(
    id               uuid primary key default gen_random_uuid(),
    first_name       text not null,
    last_name        text not null,
    email            text not null UNIQUE,
    phone            text not null UNIQUE,
    info             text             default null,
    create_time      timestamptz      default now(),
    last_modify_time timestamptz      default now()
);
comment on table users_scheme.users is 'Таблица описывающая конкретного пользователя';
comment on column users_scheme.users.id is 'Идентификатор записи в таблице';
comment on column users_scheme.users.first_name is 'Имя пользователя';
comment on column users_scheme.users.last_name is 'Фамилия пользователя';
comment on column users_scheme.users.email is 'Почта пользователя';
comment on column users_scheme.users.phone is 'Телефон пользователя';
comment on column users_scheme.users.info is 'Информация пользователя о себе';
comment on column users_scheme.users.create_time is 'Дата создания пользователя';
comment on column users_scheme.users.last_modify_time is 'Дата последнего изменения пользователя';

-- Создание таблицы подписчиков
create table IF NOT EXISTS users_scheme.subscriptions
(
    id                 uuid primary key default gen_random_uuid(),
    user_id            uuid,
    user_subscriber_id uuid,
    create_time        timestamptz      default now(),
    constraint fk_user_id foreign key (user_id) REFERENCES users_scheme.users (id),
    constraint fk_user_subscriber_id foreign key (user_subscriber_id) REFERENCES users_scheme.users (id)
);
comment on table users_scheme.subscriptions is 'Таблица описывающая связи пользователя и его подписчиков';
comment on column users_scheme.subscriptions.id is 'Идентификатор записи в таблице';
comment on column users_scheme.subscriptions.user_id is 'Идентификатор пользователя, определяющий на кого подписываются';
comment on column users_scheme.subscriptions.user_subscriber_id is 'Идентификатор пользователя, определяющий кто подписан на текущего пользователя';
comment on column users_scheme.subscriptions.create_time is 'Дата создания подписки';

-- Создание пользователя без подписчиков
insert into users_scheme.users (first_name, last_name, email, phone)
values ('Илья', 'Журавлев', 'ilya@yandex.ru', '+79271408413');

-- Создание подписчика Никита
insert into users_scheme.users (id, first_name, last_name, email, phone)
values ('202572d2-f261-11ed-a05b-0242ac120003', 'Никита', 'Подписывающий', 'nikita@yandex.ru', '+79271408414');

-- Создание пользователя с подписчиком Рома
insert into users_scheme.users (id, first_name, last_name, email, phone)
values ('a74a7726-f261-11ed-a05b-0242ac120003', 'Рома', 'Сафронов', 'roma@yandex.ru', '+79271408415');

-- Создание пользователя с подписчиком Вася
insert into users_scheme.users (id, first_name, last_name, email, phone)
values ('291253a6-f261-11ed-a05b-0242ac120003', 'Вася', 'Уважаемый', 'vasya@yandex.ru', '+79271408416');


-- Подписываем на Васю Никиту
insert into users_scheme.subscriptions (user_id, user_subscriber_id)
values ('291253a6-f261-11ed-a05b-0242ac120003', '202572d2-f261-11ed-a05b-0242ac120003');

-- Подписываем на Васю Никиту
insert into users_scheme.subscriptions (user_id, user_subscriber_id)
values ('a74a7726-f261-11ed-a05b-0242ac120003', '291253a6-f261-11ed-a05b-0242ac120003');

