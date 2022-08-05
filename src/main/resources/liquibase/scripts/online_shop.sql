--liquibase formatted sql

----changeset kulich51:table comments
create table comments
(
    id         serial primary key,
    ads_id     bigint    not null,
    author     bigint    not null,
    created_at timestamp not null,
    text       text      not null
);

----changeset kulich51:tables users and authorities
create table users
(
    username varchar(50)  not null primary key,
    password varchar(120) not null,
    enabled  boolean      not null
);

create table authorities
(
    username  varchar(50) not null,
    authority varchar(50) not null,
    foreign key (username) references users (username)
);
drop table ads;

create table ads
(
    image  text ,
    author bigint not null,
    price  bigint not null,
    pk     bigint not null,
    title  text   not null

);