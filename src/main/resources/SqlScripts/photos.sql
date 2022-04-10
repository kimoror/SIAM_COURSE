create table siam.photos
(
    id BIGSERIAL not null
        constraint """photos""_pk"
            primary key,
    user_id BIGINT not null,
    photo bytea not null,
    profile_photo BOOLEAN not null,
    post_photo BOOLEAN not null
);

alter table siam.photos
    add constraint photos_user_info_user_id_fk
        foreign key (user_id) references siam.user_info;