create table siam.RESUMES
(
    ID bigserial not null
        constraint resumes_pk
            primary key,
    RESUME bytea not null
);
