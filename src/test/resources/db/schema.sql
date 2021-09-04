-- 테이블 순서는 관계를 고려하여 한 번에 실행해도 에러가 발생하지 않게 정렬되었습니다.

-- users Table Create SQL
CREATE TABLE users
(
    `user_id`    BINARY(16)    NOT NULL    COMMENT '유저 아이디',
    `cur_point`  INT           NULL        COMMENT '현재 보유하고 있는 포인트',
    CONSTRAINT PK_users PRIMARY KEY (user_id)
);


-- reviews Table Create SQL
CREATE TABLE reviews
(
    `review_id`  BINARY(16)    NOT NULL    COMMENT '리뷰 아이디',
    `user_id`    BINARY(16)    NOT NULL    COMMENT '유저 아이디',
    `place_id`   BINARY(16)    NOT NULL    COMMENT '장소 아이디',
    `content`    LONGTEXT      NULL        COMMENT '리뷰 내용'
);

ALTER TABLE reviews COMMENT '리뷰 변경 내역 추척';

CREATE INDEX IX_reviews_1
    ON reviews(user_id, place_id);

CREATE UNIQUE INDEX UQ_reviews_1
    ON reviews(review_id);

ALTER TABLE reviews
    ADD CONSTRAINT FK_reviews_user_id_users_user_id FOREIGN KEY (user_id)
        REFERENCES users (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;


-- point_history Table Create SQL
CREATE TABLE point_history
(
    `point_history_id`  BINARY(16)     NOT NULL    COMMENT 'PointHistoryID',
    `user_id`           BINARY(16)     NULL        COMMENT 'UserID',
    `type`              VARCHAR(45)    NULL        COMMENT 'TYPE',
    `create_datetime`   TIMESTAMP      NULL        COMMENT 'CreateDateTime',
    `update_point`      INT            NULL        COMMENT 'Update History Point',
    CONSTRAINT PK_point_history PRIMARY KEY (point_history_id)
);

ALTER TABLE point_history
    ADD CONSTRAINT FK_point_history_user_id_users_user_id FOREIGN KEY (user_id)
        REFERENCES users (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;


-- images Table Create SQL
CREATE TABLE images
(
    `image_id`   BINARY(16)    NOT NULL    COMMENT '이미지 아이디',
    `review_id`  BINARY(16)    NULL        COMMENT '리뷰 아이디',
    CONSTRAINT PK_images PRIMARY KEY (image_id)
);

ALTER TABLE images COMMENT '리뷰용 사진 (변경 내역이 추적되야함)';

ALTER TABLE images
    ADD CONSTRAINT FK_images_review_id_reviews_review_id FOREIGN KEY (review_id)
        REFERENCES reviews (review_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

