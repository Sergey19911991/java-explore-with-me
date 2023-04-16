drop table if exists hit;

create table if not exists hit
(
    app
    varchar,
    uri
    varchar,
    ip
    varchar,
    timestamp
    timestamp,
    id_hit
    serial
    constraint
    hit_pk
    primary
    key
    unique
);