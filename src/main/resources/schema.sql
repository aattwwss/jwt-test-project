-- auto-generated definition
create table APP_USER
(
    ID       BIGINT auto_increment,
    USERNAME VARCHAR(32) not null,
    PASSWORD VARCHAR     not null,
    constraint USER_PK
        primary key (ID)
);

create unique index APP_USER_USERNAME_UINDEX
    on APP_USER (USERNAME);

create table app_role
(
    id   BIGINT auto_increment,
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
    id        BIGINT auto_increment primary key,
    authority varchar(32) not null
);

CREATE TABLE app_role_authority
(
    role_id      int NOT NULL references app_role (id),
    authority_id int NOT NULL references app_authority (id),
    primary key (role_id, authority_id)
);