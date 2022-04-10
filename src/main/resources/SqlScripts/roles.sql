create table ROLES
(
    ID  integer      not null
        constraint "roles_pk"
            primary key,
    NAME varchar(255) not null
);

alter table ROLES
    owner to postgres;