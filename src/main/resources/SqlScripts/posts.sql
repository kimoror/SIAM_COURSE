create table siam.POSTS
(
    ID bigserial not null
        constraint """posts""_pk"
            primary key,
    USER_ID BIGINT not null,
    TEXT TEXT,
    PHOTO_ID BIGINT
);
alter table siam.POSTS
    add constraint posts_user_info_user_id_fk
        foreign key (USER_ID) references siam.user_info;

alter table siam.POSTS
    add constraint posts_photos_id_fk
        foreign key (PHOTO_ID) references siam.photos;