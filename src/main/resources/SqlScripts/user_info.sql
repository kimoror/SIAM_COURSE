drop table if exists siam.user_info;

create table siam."USER_INFO"
(
    USER_ID BIGSERIAL not null
        constraint user_info_pk
            primary key references siam.users(ID),
    NAME VARCHAR(70) not null,
    SURNAME VARCHAR(70),
    MIDDLE_NAME VARCHAR(70),
    BIRHTHDAY DATE not null,
    ADDRESS TEXT,
    ROLE VARCHAR(50) not null,
    STATUS VARCHAR(10) not null,
    COMPANY_ID BIGINT
        constraint user_company_id_fk
            references siam.COMPANY (ID),
    WORK_POSITION TEXT,
    EDUCATION TEXT,
    SCHOOL TEXT,
    UNIVERSITY TEXT,
    PHONE_NUMBER VARCHAR(20)
);

create unique index user_info_company_id_uindex
    on siam.user_info (COMPANY_ID);

