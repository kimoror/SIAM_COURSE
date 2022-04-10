drop table if exists siam.company cascade;

create table siam.company
(
    id BIGSERIAL not null
        constraint company_pk
        primary key,
    NAME TEXT not null,
    address TEXT not null,
    ACTIVITY_FIELD VARCHAR(255) not null,
    DESCRIPTION TEXT
);

create unique index company_name_uindex
    on siam.company (name);