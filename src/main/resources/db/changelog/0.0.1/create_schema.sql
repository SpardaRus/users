--liquibase formatted sql

--changeset yasakovaa:TASK-1
--comment Создание схемы пользователей
create schema IF NOT EXISTS users_scheme;