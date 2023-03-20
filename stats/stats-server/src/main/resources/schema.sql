drop table if exists hit;

create table if not exists hit
(
    app      varchar,
    uri      varchar,
    ip       varchar,
    timestamp varchar,
    id_hit   bigserial
        constraint hit_pk
            primary key
);