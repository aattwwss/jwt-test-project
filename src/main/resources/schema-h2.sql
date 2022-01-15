-- auto-generated definition
create table USER
(
    ID       BIGINT auto_increment,
    USERNAME VARCHAR(32) not null,
    PASSWORD VARCHAR     not null,
    constraint USER_PK
        primary key (ID)
);

create unique index USER_USERNAME_UINDEX
    on USER (USERNAME);

