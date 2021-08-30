CREATE TABLE PLACES
(
    `place_id`  BINARY(16)    NOT NULL    COMMENT 'PlaceID',
    PRIMARY KEY (place_id)
);

CREATE TABLE USERS
(
    `user_id`  BINARY(16)    NOT NULL    COMMENT 'userID',
    PRIMARY KEY (user_id)
);

CREATE TABLE REVIEWS
(
    `review_id`  BINARY(16)    NOT NULL    COMMENT 'ReviewID',
    `content`    LONGTEXT      NULL        COMMENT 'Content',
    `user_id`    BINARY(16)    NULL        COMMENT 'userID',
    `place_id`   BINARY(16)    NULL        COMMENT 'PlaceID',
    PRIMARY KEY (review_id)
);

ALTER TABLE REVIEWS
    ADD CONSTRAINT FK_REVIEWS_user_id_USERS_user_id FOREIGN KEY (user_id)
        REFERENCES USERS (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE REVIEWS
    ADD CONSTRAINT FK_REVIEWS_place_id_PLACES_place_id FOREIGN KEY (place_id)
        REFERENCES PLACES (place_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

CREATE TABLE POINT_HISTORY
(
    `point_history_id`  BINARY(16)     NOT NULL    COMMENT 'PointHistoryID',
    `type`              VARCHAR(45)    NULL        COMMENT 'TYPE',
    `current_point`     INT            NULL        COMMENT 'CurrentPoint',
    `create_datetime`   TIMESTAMP      NULL        COMMENT 'CreateDateTime',
    `user_id`           BINARY(16)     NULL        COMMENT 'UserID',
    `update_point`      INT            NULL        COMMENT 'Update History Point',
    PRIMARY KEY (point_history_id)
);

ALTER TABLE POINT_HISTORY
    ADD CONSTRAINT FK_POINT_HISTORY_user_id_USERS_user_id FOREIGN KEY (user_id)
        REFERENCES USERS (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;


CREATE TABLE IMAGES
(
    `image_id`   BINARY(16)      NOT NULL    COMMENT 'ImageID',
    `url`        VARCHAR(255)    NULL        COMMENT 'URL',
    `review_id`  BINARY(16)      NULL        COMMENT 'ReviewID',
    PRIMARY KEY (image_id)
);

ALTER TABLE IMAGES
    ADD CONSTRAINT FK_IMAGES_review_id_REVIEWS_review_id FOREIGN KEY (review_id)
        REFERENCES REVIEWS (review_id) ON DELETE RESTRICT ON UPDATE RESTRICT;
