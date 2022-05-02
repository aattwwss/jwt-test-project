set schema 'app';

create table app_user
(
    id       serial primary key,
    username varchar(32) not null,
    password varchar     not null
);

create unique index app_user_username_uindex
    on app_user (username);

create table app_role
(
    id   serial primary key,
    role varchar(32) not null
);

CREATE TABLE app_user_role
(
    user_id int NOT NULL references app_user (id),
    role_id int NOT NULL references app_role (id),
    primary key (user_id, role_id)
);

create table app_authority
(
    id        serial primary key,
    authority varchar(32) not null
);

CREATE TABLE app_role_authority
(
    role_id      int NOT NULL references app_role (id),
    authority_id int NOT NULL references app_authority (id),
    primary key (role_id, authority_id)
);

