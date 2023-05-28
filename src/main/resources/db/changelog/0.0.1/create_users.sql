--liquibase formatted sql

--changeset yasakovaa:TASK-2
--comment Создание таблицы пользователей
create table IF NOT EXISTS users
(
    id               uuid primary key default gen_random_uuid(),
    first_name       text        not null,
    last_name        text        not null,
    email            text        not null UNIQUE,
    phone            text        not null UNIQUE,
    info             text             default null,
    deleted          bool        not null,
    create_time      timestamptz not null,
    last_modify_time timestamptz not null
);
comment on table users is 'Таблица описывающая конкретного пользователя';
comment on column users.id is 'Идентификатор записи в таблице';
comment on column users.first_name is 'Имя пользователя';
comment on column users.last_name is 'Фамилия пользователя';
comment on column users.email is 'Почта пользователя';
comment on column users.phone is 'Телефон пользователя';
comment on column users.info is 'Информация пользователя о себе';
comment on column users.deleted is 'Признак удаления записи. true - удалена';
comment on column users.create_time is 'Дата создания пользователя';
comment on column users.last_modify_time is 'Дата последнего изменения пользователя';