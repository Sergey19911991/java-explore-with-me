drop table if exists event_compolation;
drop table if exists compilations;
drop table if exists requests;
drop table if exists events;
drop table if exists users;
drop table if exists categories;

create table if not exists users
(
    id
    serial
    constraint
    users_pk
    primary
    key,
    name
    varchar,
    email
    varchar
);

create table if not exists categories
(
    id
    serial
    constraint
    categories_pk
    primary
    key,
    name
    varchar
);


create table if not exists events
(
    annotation
    varchar,
    title
    varchar,
    description
    varchar,
    event_date
    timestamp,
    paid
    boolean,
    id
    serial
    constraint
    events_pk
    primary
    key
    unique,
    created_on
    timestamp,
    request_moderation
    boolean,
    published_on
    timestamp,
    id_initiator
    integer
    constraint
    events_users_id_fk
    references
    users,
    id_category
    integer
    constraint
    events_categories_id_fk
    references
    categories,
    participant_limit
    integer,
    state
    varchar,
    lat
    double
    precision,
    lon
    double
    precision
);
create table if not exists requests
(
    id
    serial
    constraint
    requests_pk
    primary
    key
    unique,
    created
    varchar,
    event
    integer,
    requester
    integer,
    status
    varchar
);
create table if not exists compilations
(
    id_compilation
    serial
    constraint
    compilations_pk
    primary
    key
    unique,
    title
    varchar,
    pinned
    boolean
);
create table if not exists event_compolation
(
    id
    integer
    constraint
    event_compolation_events_id_fk
    references
    events,
    id_compilation
    integer
    constraint
    event_compolation_compilations_id_fk
    references
    compilations
);


