create table siam.offers
(
    id bigserial not null
        constraint """offers""_pk"
            primary key,
    from_user_id bigint not null
        constraint """offers""_user_info_user_id_fk"
            references siam.user_info,
    to_user_id bigint not null
        constraint """offers""_user_info_user_id_fk_2"
            references siam.user_info,
    price int not null,
    COVER_LETTER TEXT
);