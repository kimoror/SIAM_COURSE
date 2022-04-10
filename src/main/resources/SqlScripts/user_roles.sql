create table siam."USER_ROLES"
(
    USER_ID bigint not null,
    ROLE_ID int not null
);

alter table siam.user_roles
    add constraint user_roles_users_id_fk
        foreign key (USER_ID) references siam.users;

alter table siam.user_roles
    add constraint user_roles_roles_id_fk
        foreign key (ROLE_ID) references siam.roles;