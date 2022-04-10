drop table if exists siam.USER;

create table USERS
(
    ID       bigserial not null
        constraint user_pk
            primary key,
    EMAIL   varchar(255) not null,
    PASSWORD varchar(255) not null
);

alter table USERS
    owner to postgres;

create unique index user_email_uindex
    on USERS (EMAIL);