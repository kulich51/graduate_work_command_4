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

--changeset kulich51:table_users_profiles runOnChange:true
drop table if exists users_profiles;
create table users_profiles
(
    id serial primary key,
    email varchar(50),
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    phone varchar(20) not null,
    foreign key (email) references users (username),
    constraint unique_email unique (email)
);

--changeset kulich51:table_ads runOnChange:true
drop table if exists ads;
create table ads
(
    id      serial primary key,
    user_id bigint not null,
    title   text not null,
    image   text not null,
    price   integer not null,
    foreign key (user_id) references users_profiles(id)
);

--changeset kulich51:table_ads_rename_user_id
alter table ads rename column user_id to user_profile_id;

--changeset kulich51:comments_add_fk
alter table comments add constraint fk_ads foreign key (ads_id) references ads(id);

--changeset kulich51:comments_add_fk_to_user_profiles
alter table comments rename column author to user_profile_id;
alter table comments add constraint fk_ads_user_profiles foreign key (user_profile_id) references users_profiles(id);

--changeset kulich51:ads_create_description_column
alter table ads add column description text;

--changeset kulich51:image_table
create table image
(
    id          serial primary key,
    data        bytea,
    media_type  varchar(255),
    file_size   bigint
);

--changeset kulich51:ads_add_image
alter table ads drop column image;
alter table ads add column image_id bigint;
alter table ads add constraint fk_ads_image foreign key (image_id) references image(id);

--changeset kulich51:user_profiles_drop_not_null
alter table users_profiles alter column first_name drop not null,
                           alter column last_name drop not null,
                           alter column phone drop not null;