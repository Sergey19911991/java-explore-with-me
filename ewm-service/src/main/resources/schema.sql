
drop table if exists users;
drop table if exists categories;

create table if not exists users
(
    id    serial
        constraint users_pk
            primary key,
    name  varchar,
    email varchar
);

create table if not exists categories
(
    id   serial
        constraint categories_pk
        primary key,
    name varchar
);