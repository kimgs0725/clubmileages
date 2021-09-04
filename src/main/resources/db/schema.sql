create table images (
    image_id BINARY(16) not null,
    user_id BINARY(16),
    place_id BINARY(16),
    primary key (image_id)
) engine=InnoDB;

create table point_history (
    point_history_id BINARY(16) not null,
    create_datetime datetime,
    type varchar(255),
    update_point integer,
    user_id BINARY(16) not null,
    primary key (point_history_id)
) engine=InnoDB;

create table reviews (
    place_id BINARY(16) not null,
    user_id BINARY(16) not null,
    content longtext,
    is_first_review bit,
    review_id BINARY(16),
    primary key (place_id, user_id)
) engine=InnoDB;

create index IX_REVIEWS_1 on reviews(user_id, place_id);

create table users (
    user_id BINARY(16) not null,
    cur_point integer DEFAULT 0,
    primary key (user_id)
) engine=InnoDB;

alter table reviews
    add constraint UKk9uybr3xsg89kumpddtq66o5e
        unique (review_id);

alter table images
    add constraint FKcejehenjofsv7wq87vwnweos foreign key (user_id, place_id)
        references reviews (place_id, user_id);

alter table point_history
    add constraint FK_POINT_HISTORY_user_id_USERS_user_id foreign key (user_id)
        references users (user_id);