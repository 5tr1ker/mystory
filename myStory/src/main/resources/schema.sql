CREATE TABLE IF NOT EXISTS `profile`
(
    `profile_key` BIGINT       NOT NULL AUTO_INCREMENT,
    `email`       VARCHAR(255) NULL DEFAULT NULL,
    `options`     INT          NOT NULL,
    `phone`       VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`profile_key`)
);

CREATE TABLE IF NOT EXISTS `user`
(
    `user_key`            BIGINT       NOT NULL AUTO_INCREMENT,
    `id`                  VARCHAR(30)  NOT NULL,
    `join_date`           DATE         NOT NULL,
    `password`            VARCHAR(255) NOT NULL,
    `suspension_date`     DATE,
    `suspension_reason`   VARCHAR(255),
    `is_suspension`       VARCHAR(5)   NOT NULL,
    `profile_image`       VARCHAR(255) NULL DEFAULT NULL,
    `role`                VARCHAR(255) NOT NULL,
    `user_type`           VARCHAR(255) NOT NULL,
    `profile_profile_key` BIGINT       NOT NULL,
    PRIMARY KEY (`user_key`),
    FOREIGN KEY (`profile_profile_key`) REFERENCES `profile` (`profile_key`)
);

CREATE TABLE IF NOT EXISTS `bug_report` (
    bug_report_id bigint    not null auto_increment,
    content varchar(255)    not null,
    is_solved varchar(255)  not null,
    report_time             datetime(6),
    reporter_user_key       bigint,
    primary key (bug_report_id),
    foreign key (reporter_user_key) references user (user_key)
);

CREATE TABLE IF NOT EXISTS `content_report`
(
    content_report_id bigint not null auto_increment,
    content varchar(255) not null,
    is_action varchar(255) not null,
    report_type varchar(255),
    report_time datetime(6),
    report_data_report_data_id bigint,
    reporter_user_key bigint,
    primary key (content_report_id),
    foreign key (report_data_report_data_id) references report_data (report_data_id),
    foreign key (reporter_user_key) references user (user_key)
);

CREATE TABLE IF NOT EXISTS `mail_cert` (
    mail_cert_id bigint not null auto_increment,
    id VARCHAR(255) NOT NULL,
    verification_code VARCHAR(255) NOT NULL,
    primary key (mail_cert_id)
);

CREATE TABLE IF NOT EXISTS `report_data` (
    report_data_id bigint not null auto_increment,
    content varchar(255),
    title varchar(255),
    target_user_key bigint,
    primary key (report_data_id),
    foreign key (target_user_key) references user (user_key)
);

CREATE TABLE IF NOT EXISTS `post`
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

CREATE TABLE IF NOT EXISTS `attachment`
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

CREATE TABLE IF NOT EXISTS `meeting`
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
CREATE TABLE IF NOT EXISTS `chat_room`
(
    `chat_id`               BIGINT NOT NULL AUTO_INCREMENT,
    `create_date`             DATE   NULL DEFAULT NULL,
    `meeting_id_meeting_id` BIGINT NULL DEFAULT NULL,
    PRIMARY KEY (`chat_id`),
    FOREIGN KEY (`meeting_id_meeting_id`) REFERENCES `meeting` (`meeting_id`)
);

# ALTER TABLE `meeting` ADD CONSTRAINT `MEETINGFORIEGNKEY` FOREIGN KEY(`chat_room_chat_id`) REFERENCES `chat_room` (`chat_id`);

CREATE TABLE IF NOT EXISTS `chat`
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

CREATE TABLE IF NOT EXISTS `chat_room_chat_data`
(
    `chat_room_chat_id` BIGINT NOT NULL,
    `chat_data_chat_id` BIGINT NOT NULL,
    FOREIGN KEY (`chat_room_chat_id`) REFERENCES `chat_room` (`chat_id`),
    FOREIGN KEY (`chat_data_chat_id`) REFERENCES `chat` (`chat_id`)
);

CREATE TABLE IF NOT EXISTS `comment`
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

CREATE TABLE IF NOT EXISTS `meeting_participant`
(
    `meeting_participant_id` BIGINT NOT NULL AUTO_INCREMENT,
    `meeting_id`             BIGINT NULL DEFAULT NULL,
    `user_id`                BIGINT NULL DEFAULT NULL,
    PRIMARY KEY (`meeting_participant_id`),
    FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`meeting_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_key`)
);

CREATE TABLE IF NOT EXISTS `post_recommendation`
(
    `post_post_id`            BIGINT NOT NULL,
    `recommendation_user_key` BIGINT NOT NULL,
    INDEX `FKjx9y24ucmuk0jhifttugnevq6` (`post_post_id` ASC) VISIBLE,
    FOREIGN KEY (`post_post_id`) REFERENCES `post` (`post_id`),
    FOREIGN KEY (`recommendation_user_key`) REFERENCES `user` (`user_key`)
);

CREATE TABLE IF NOT EXISTS `tag`
(
    `tag_id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `tag_data` VARCHAR(15) NOT NULL,
    PRIMARY KEY (`tag_id`)
);

CREATE TABLE IF NOT EXISTS `post_tag`
(
    `post_post_id` BIGINT NOT NULL,
    `tag_tag_id`   BIGINT NOT NULL,
    FOREIGN KEY (`tag_tag_id`) REFERENCES `tag` (`tag_id`),
    FOREIGN KEY (`post_post_id`) REFERENCES `post` (`post_id`)
);

CREATE TABLE IF NOT EXISTS `refresh_token`
(
    `token_id`  BIGINT       NOT NULL AUTO_INCREMENT,
    `key_email` VARCHAR(255) NOT NULL,
    `token`     VARCHAR(255) NOT NULL,
    PRIMARY KEY (`token_id`)
);

CREATE TABLE IF NOT EXISTS `visitant`
(
    visitant_id bigint not null auto_increment,
    user_agent varchar(255),
    user_ip varchar(255),
    visit_date date,
    primary key (visitant_id)
);

CREATE TABLE IF NOT EXISTS `reservation`
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

CREATE TABLE IF NOT EXISTS `reservation_participants`
(
    `reservation_participants_id` BIGINT NOT NULL AUTO_INCREMENT,
    `reservation_reservation_id`  BIGINT NULL DEFAULT NULL,
    `user_user_key`               BIGINT NULL DEFAULT NULL,
    PRIMARY KEY (`reservation_participants_id`),
    FOREIGN KEY (`reservation_reservation_id`) REFERENCES `reservation` (`reservation_id`),
    FOREIGN KEY (`user_user_key`) REFERENCES `user` (`user_key`)
);