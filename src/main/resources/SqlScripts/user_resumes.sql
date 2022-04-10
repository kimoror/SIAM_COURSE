create table siam."USER_RESUMES"
(
    USER_ID BIGINT not null,
    RESUME_ID bigint not null
);

alter table siam.user_resumes
    add constraint user_resumes_resumes_id_fk
        foreign key (RESUME_ID) references siam.RESUMES;

alter table siam.user_resumes
    add constraint user_resumes_user_info_user_id_fk
        foreign key (USER_ID) references siam.USER_INFO;