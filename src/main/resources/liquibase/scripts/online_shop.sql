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

--changeset kulich51:tables users and authorities
create table users
(
    username    varchar(50) not null primary key ,
    password    varchar(120) not null,
    enabled     boolean not null
);

create table authorities
(
    username varchar(50) not null,
    authority varchar(50) not null,
    foreign key (username) references users (username)
);

--changeset kulich51:table_users_profiles
create table users_profiles
(
    id serial primary key,
    email varchar(50),
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    phone varchar(20) not null,
    foreign key (email) references users (username)
);
