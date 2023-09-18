CREATE TABLE `profile`
(
    `profile_key` BIGINT       NOT NULL AUTO_INCREMENT,
    `email`       VARCHAR(255) NULL DEFAULT NULL,
    `options`     INT          NOT NULL,
    `phone`       VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`profile_key`)
);

CREATE TABLE `user`
(
    `user_key`            BIGINT       NOT NULL AUTO_INCREMENT,
    `id`                  VARCHAR(30)  NOT NULL,
    `join_date`           DATE         NOT NULL,
    `password`            VARCHAR(255) NOT NULL,
    `profile_image`       VARCHAR(255) NULL DEFAULT NULL,
    `role`                VARCHAR(255) NOT NULL,
    `user_type`           VARCHAR(255) NOT NULL,
    `profile_profile_key` BIGINT       NOT NULL,
    PRIMARY KEY (`user_key`),
    FOREIGN KEY (`profile_profile_key`) REFERENCES `profile` (`profile_key`)
);

CREATE TABLE `post`
(
    `post_id`          BIGINT        NOT NULL AUTO_INCREMENT,
    `content`          VARCHAR(1100) NOT NULL,
    `is_block_comment` VARCHAR(255)  NOT NULL,
    `is_private`       VARCHAR(255)  NOT NULL,
    `likes`            INT           NOT NULL,
    `post_date`        DATETIME(6)   NULL DEFAULT NULL,
    `title`            VARCHAR(50)   NOT NULL,
    `views`            INT           NOT NULL,
    `writer_user_key`  BIGINT        NULL DEFAULT NULL,
    PRIMARY KEY (`post_id`),
    FOREIGN KEY (`writer_user_key`) REFERENCES `user` (`user_key`)
);

CREATE TABLE `attachment`
(
    `attachment_id`  BIGINT       NOT NULL AUTO_INCREMENT,
    `file_size`      BIGINT       NOT NULL,
    `real_file_name` VARCHAR(50)  NOT NULL,
    `s3url`          VARCHAR(100) NOT NULL,
    `uuid_file_name` VARCHAR(50)  NOT NULL,
    `post_post_id`   BIGINT       NOT NULL,
    PRIMARY KEY (`attachment_id`),
    FOREIGN KEY (`post_post_id`) REFERENCES `post` (`post_id`)
);

CREATE TABLE `meeting`
(
    `meeting_id`             BIGINT       NOT NULL AUTO_INCREMENT,
    `address`                VARCHAR(255) NOT NULL,
    `description`            VARCHAR(255) NULL DEFAULT NULL,
    `detail_address`         VARCHAR(255) NULL DEFAULT NULL,
    `locatex`                DOUBLE       NOT NULL,
    `locatey`                DOUBLE       NOT NULL,
    `max_participants`       INT          NOT NULL,
    `meeting_image`          VARCHAR(255) NULL DEFAULT NULL,
    `title`                  VARCHAR(255) NULL DEFAULT NULL,
    `chat_room_chat_id`      BIGINT       NULL DEFAULT NULL,
    `meeting_owner_user_key` BIGINT       NULL DEFAULT NULL,
    PRIMARY KEY (`meeting_id`),
    FOREIGN KEY (`meeting_owner_user_key`) REFERENCES `user` (`user_key`)
);
CREATE TABLE `chat_room`
(
    `chat_id`               BIGINT NOT NULL AUTO_INCREMENT,
    `create_date`             DATE   NULL DEFAULT NULL,
    `meeting_id_meeting_id` BIGINT NULL DEFAULT NULL,
    PRIMARY KEY (`chat_id`),
    FOREIGN KEY (`meeting_id_meeting_id`) REFERENCES `meeting` (`meeting_id`)
);

ALTER TABLE `meeting` ADD CONSTRAINT `MEETINGFORIEGNKEY` FOREIGN KEY(`chat_room_chat_id`) REFERENCES `chat_room` (`chat_id`);

CREATE TABLE `chat`
(
    `chat_id`               BIGINT       NOT NULL AUTO_INCREMENT,
    `message`               VARCHAR(255) NULL DEFAULT NULL,
    `send_time`             VARCHAR(255) NULL DEFAULT NULL,
    `sender`                VARCHAR(255) NULL DEFAULT NULL,
    `sender_image`          VARCHAR(255) NULL DEFAULT NULL,
    `meeting_id_meeting_id` BIGINT       NULL DEFAULT NULL,
    PRIMARY KEY (`chat_id`),
    FOREIGN KEY (`meeting_id_meeting_id`) REFERENCES `meeting` (`meeting_id`)
);

CREATE TABLE `chat_room_chat_data`
(
    `chat_room_chat_id` BIGINT NOT NULL,
    `chat_data_chat_id` BIGINT NOT NULL,
    FOREIGN KEY (`chat_room_chat_id`) REFERENCES `chat_room` (`chat_id`),
    FOREIGN KEY (`chat_data_chat_id`) REFERENCES `chat` (`chat_id`)
);

CREATE TABLE `comment`
(
    `comment_id`      BIGINT       NOT NULL AUTO_INCREMENT,
    `content`         VARCHAR(200) NOT NULL,
    `post_date`       DATETIME(6)  NULL DEFAULT NULL,
    `post_post_id`    BIGINT       NOT NULL,
    `writer_user_key` BIGINT       NULL DEFAULT NULL,
    PRIMARY KEY (`comment_id`),
    FOREIGN KEY (`post_post_id`) REFERENCES `post` (`post_id`),
    FOREIGN KEY (`writer_user_key`) REFERENCES `user` (`user_key`)
);

CREATE TABLE `meeting_participant`
(
    `meeting_participant_id` BIGINT NOT NULL AUTO_INCREMENT,
    `meeting_id`             BIGINT NULL DEFAULT NULL,
    `user_id`                BIGINT NULL DEFAULT NULL,
    PRIMARY KEY (`meeting_participant_id`),
    FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`meeting_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_key`)
);

CREATE TABLE `post_recommendation`
(
    `post_post_id`            BIGINT NOT NULL,
    `recommendation_user_key` BIGINT NOT NULL,
    INDEX `FKjx9y24ucmuk0jhifttugnevq6` (`post_post_id` ASC) VISIBLE,
    FOREIGN KEY (`post_post_id`) REFERENCES `post` (`post_id`),
    FOREIGN KEY (`recommendation_user_key`) REFERENCES `user` (`user_key`)
);

CREATE TABLE `tag`
(
    `tag_id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `tag_data` VARCHAR(15) NOT NULL,
    PRIMARY KEY (`tag_id`)
);

CREATE TABLE `post_tag`
(
    `post_post_id` BIGINT NOT NULL,
    `tag_tag_id`   BIGINT NOT NULL,
    FOREIGN KEY (`tag_tag_id`) REFERENCES `tag` (`tag_id`),
    FOREIGN KEY (`post_post_id`) REFERENCES `post` (`post_id`)
);

CREATE TABLE `refresh_token`
(
    `token_id`  BIGINT       NOT NULL AUTO_INCREMENT,
    `key_email` VARCHAR(255) NOT NULL,
    `token`     VARCHAR(255) NOT NULL,
    PRIMARY KEY (`token_id`)
);

CREATE TABLE `reservation`
(
    `reservation_id`      BIGINT       NOT NULL AUTO_INCREMENT,
    `address`             VARCHAR(255) NULL DEFAULT NULL,
    `date`                DATETIME(6)  NULL DEFAULT NULL,
    `description`         VARCHAR(255) NULL DEFAULT NULL,
    `detail_address`      VARCHAR(255) NULL DEFAULT NULL,
    `locatex`             VARCHAR(255) NULL DEFAULT NULL,
    `locatey`             VARCHAR(255) NULL DEFAULT NULL,
    `max_participants`    INT          NOT NULL,
    `meetings_meeting_id` BIGINT       NULL DEFAULT NULL,
    PRIMARY KEY (`reservation_id`),
    FOREIGN KEY (`meetings_meeting_id`) REFERENCES `meeting` (`meeting_id`)
);

CREATE TABLE `reservation_participants`
(
    `reservation_participants_id` BIGINT NOT NULL,
    `reservation_reservation_id`  BIGINT NULL DEFAULT NULL,
    `user_user_key`               BIGINT NULL DEFAULT NULL,
    PRIMARY KEY (`reservation_participants_id`),
    FOREIGN KEY (`reservation_reservation_id`) REFERENCES `reservation` (`reservation_id`),
    FOREIGN KEY (`user_user_key`) REFERENCES `user` (`user_key`)
);

CREATE TABLE `reservation_participants_seq`
(
    `next_val` BIGINT NULL DEFAULT NULL
);
