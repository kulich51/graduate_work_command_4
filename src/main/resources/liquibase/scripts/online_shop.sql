--liquibase formatted sql

--changeset kulich51:table comments
create table comments
(
    id           serial primary key,
    ads_id       bigint not null,
    author       bigint not null ,
    created_at   timestamp not null,
    text         text not null
);