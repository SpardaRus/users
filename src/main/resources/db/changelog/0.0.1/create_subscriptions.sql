--liquibase formatted sql

--changeset yasakovaa:TASK-3
--comment Создание таблицы подписчиков
create table IF NOT EXISTS users_scheme.subscriptions
(
    id                 uuid primary key default gen_random_uuid(),
    user_id            uuid,
    user_subscriber_id uuid,
    deleted            bool      not null,
    create_time        timestamp not null,
    last_modify_time   timestamp not null,
    constraint fk_user_id foreign key (user_id) REFERENCES users_scheme.users (id),
    constraint fk_user_subscriber_id foreign key (user_subscriber_id) REFERENCES users_scheme.users (id),
    UNIQUE (user_id, user_subscriber_id)
);
comment on table users_scheme.subscriptions is 'Таблица описывающая связи пользователя и его подписчиков';
comment on column users_scheme.subscriptions.id is 'Идентификатор записи в таблице';
comment on column users_scheme.subscriptions.user_id is 'Идентификатор пользователя, определяющий на кого подписываются';
comment on column users_scheme.subscriptions.user_subscriber_id is 'Идентификатор пользователя, определяющий кто подписан на текущего пользователя';
comment on column users_scheme.subscriptions.deleted is 'Признак удаления записи. true - удалена';
comment on column users_scheme.subscriptions.create_time is 'Дата создания подписки';
comment on column users_scheme.subscriptions.last_modify_time is 'Дата последнего изменения подписки';

-- Подписываем на Васю Никиту
insert into users_scheme.subscriptions (user_id, user_subscriber_id, deleted, create_time, last_modify_time)
values ('291253a6-f261-11ed-a05b-0242ac120003', '202572d2-f261-11ed-a05b-0242ac120003', false, '2023-05-20 00:00:00',
        '2023-05-20 00:00:00');

-- Подписываем на Рому Васю
insert into users_scheme.subscriptions (user_id, user_subscriber_id, deleted, create_time, last_modify_time)
values ('a74a7726-f261-11ed-a05b-0242ac120003', '291253a6-f261-11ed-a05b-0242ac120003', false, '2023-05-20 00:00:00',
        '2023-05-20 00:00:00');