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

