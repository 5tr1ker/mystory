USE `mystory`;

CREATE TABLE IF NOT EXISTS `mystory`.`profile`
(
    `profile_key` BIGINT       NOT NULL AUTO_INCREMENT,
    `email`       VARCHAR(255) NULL DEFAULT NULL,
    `options`     INT          NOT NULL,
    `phone`       VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`profile_key`)
);

CREATE TABLE IF NOT EXISTS `mystory`.`user`
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
    INDEX `FK2hcgqi8iq1ms6q4166xamo88g` (`profile_profile_key` ASC) VISIBLE,
    CONSTRAINT `FK2hcgqi8iq1ms6q4166xamo88g`
        FOREIGN KEY (`profile_profile_key`)
            REFERENCES `mystory`.`profile` (`profile_key`)
);

CREATE TABLE IF NOT EXISTS `mystory`.`post`
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
    INDEX `FKlp81xxsilmv0wcmmiseh3rsio` (`writer_user_key` ASC) VISIBLE,
    CONSTRAINT `FKlp81xxsilmv0wcmmiseh3rsio`
        FOREIGN KEY (`writer_user_key`)
            REFERENCES `mystory`.`user` (`user_key`)
);

CREATE TABLE IF NOT EXISTS `mystory`.`attachment`
(
    `attachment_id`  BIGINT       NOT NULL AUTO_INCREMENT,
    `file_size`      BIGINT       NOT NULL,
    `real_file_name` VARCHAR(50)  NOT NULL,
    `s3url`          VARCHAR(100) NOT NULL,
    `uuid_file_name` VARCHAR(50)  NOT NULL,
    `post_post_id`   BIGINT       NOT NULL,
    PRIMARY KEY (`attachment_id`),
    INDEX `FKc3q85kavbh8lcvbup4502my8h` (`post_post_id` ASC) VISIBLE,
    CONSTRAINT `FKc3q85kavbh8lcvbup4502my8h`
        FOREIGN KEY (`post_post_id`)
            REFERENCES `mystory`.`post` (`post_id`)
);

CREATE TABLE IF NOT EXISTS `mystory`.`chat_room`
(
    `chat_id`               BIGINT NOT NULL AUTO_INCREMENT,
    `last_chat`             DATE   NULL DEFAULT NULL,
    `meeting_id_meeting_id` BIGINT NULL DEFAULT NULL,
    PRIMARY KEY (`chat_id`),
    INDEX `FKsdnli3jk4g4h8oxbh42gkkyvi` (`meeting_id_meeting_id` ASC) VISIBLE,
    CONSTRAINT `FKsdnli3jk4g4h8oxbh42gkkyvi`
        FOREIGN KEY (`meeting_id_meeting_id`)
            REFERENCES `mystory`.`meeting` (`meeting_id`)
);

CREATE TABLE IF NOT EXISTS `mystory`.`meeting`
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
    INDEX `FKcwiieay4j5c4w5ah19c1xty3n` (`chat_room_chat_id` ASC) VISIBLE,
    INDEX `FKt27svdt5nlktiba39cyd7uf5l` (`meeting_owner_user_key` ASC) VISIBLE,
    CONSTRAINT `FKcwiieay4j5c4w5ah19c1xty3n`
        FOREIGN KEY (`chat_room_chat_id`)
            REFERENCES `mystory`.`chat_room` (`chat_id`),
    CONSTRAINT `FKt27svdt5nlktiba39cyd7uf5l`
        FOREIGN KEY (`meeting_owner_user_key`)
            REFERENCES `mystory`.`user` (`user_key`)
);

CREATE TABLE IF NOT EXISTS `mystory`.`chat`
(
    `chat_id`               BIGINT       NOT NULL AUTO_INCREMENT,
    `message`               VARCHAR(255) NULL DEFAULT NULL,
    `send_time`             VARCHAR(255) NULL DEFAULT NULL,
    `sender`                VARCHAR(255) NULL DEFAULT NULL,
    `sender_image`          VARCHAR(255) NULL DEFAULT NULL,
    `meeting_id_meeting_id` BIGINT       NULL DEFAULT NULL,
    PRIMARY KEY (`chat_id`),
    INDEX `FKltlqaui6i27wp1a43xdd9478i` (`meeting_id_meeting_id` ASC) VISIBLE,
    CONSTRAINT `FKltlqaui6i27wp1a43xdd9478i`
        FOREIGN KEY (`meeting_id_meeting_id`)
            REFERENCES `mystory`.`meeting` (`meeting_id`)
);

CREATE TABLE IF NOT EXISTS `mystory`.`chat_room_chat_data`
(
    `chat_room_chat_id` BIGINT NOT NULL,
    `chat_data_chat_id` BIGINT NOT NULL,
    UNIQUE INDEX `UK_rsxhay5xi30vex6p26lg6psdn` (`chat_data_chat_id` ASC) VISIBLE,
    INDEX `FK5uuqrejwrdkjfeoyc3rofvcxy` (`chat_room_chat_id` ASC) VISIBLE,
    CONSTRAINT `FK5uuqrejwrdkjfeoyc3rofvcxy`
        FOREIGN KEY (`chat_room_chat_id`)
            REFERENCES `mystory`.`chat_room` (`chat_id`),
    CONSTRAINT `FK7grbxtm97agookhbtbqkra9gp`
        FOREIGN KEY (`chat_data_chat_id`)
            REFERENCES `mystory`.`chat` (`chat_id`)
);

CREATE TABLE IF NOT EXISTS `mystory`.`comment`
(
    `comment_id`      BIGINT       NOT NULL AUTO_INCREMENT,
    `content`         VARCHAR(200) NOT NULL,
    `post_date`       DATETIME(6)  NULL DEFAULT NULL,
    `post_post_id`    BIGINT       NOT NULL,
    `writer_user_key` BIGINT       NULL DEFAULT NULL,
    PRIMARY KEY (`comment_id`),
    INDEX `FKl8nbwgv77jgcnpgklda5ajghi` (`post_post_id` ASC) VISIBLE,
    INDEX `FKra3p7gj3v0il648bjklivjsil` (`writer_user_key` ASC) VISIBLE,
    CONSTRAINT `FKl8nbwgv77jgcnpgklda5ajghi`
        FOREIGN KEY (`post_post_id`)
            REFERENCES `mystory`.`post` (`post_id`),
    CONSTRAINT `FKra3p7gj3v0il648bjklivjsil`
        FOREIGN KEY (`writer_user_key`)
            REFERENCES `mystory`.`user` (`user_key`)
);

CREATE TABLE IF NOT EXISTS `mystory`.`meeting_participant`
(
    `meeting_participant_id` BIGINT NOT NULL AUTO_INCREMENT,
    `meeting_id`             BIGINT NULL DEFAULT NULL,
    `user_id`                BIGINT NULL DEFAULT NULL,
    PRIMARY KEY (`meeting_participant_id`),
    INDEX `FK26jnnkay5w0sko7x7kqu17xbr` (`meeting_id` ASC) VISIBLE,
    INDEX `FKjeo2jsdsc36kbijt1cc46cdpf` (`user_id` ASC) VISIBLE,
    CONSTRAINT `FK26jnnkay5w0sko7x7kqu17xbr`
        FOREIGN KEY (`meeting_id`)
            REFERENCES `mystory`.`meeting` (`meeting_id`),
    CONSTRAINT `FKjeo2jsdsc36kbijt1cc46cdpf`
        FOREIGN KEY (`user_id`)
            REFERENCES `mystory`.`user` (`user_key`)
);

CREATE TABLE IF NOT EXISTS `mystory`.`post_recommendation`
(
    `post_post_id`            BIGINT NOT NULL,
    `recommendation_user_key` BIGINT NOT NULL,
    UNIQUE INDEX `UK_8062wws9tslggjp556tg0uuhr` (`recommendation_user_key` ASC) VISIBLE,
    INDEX `FKjx9y24ucmuk0jhifttugnevq6` (`post_post_id` ASC) VISIBLE,
    CONSTRAINT `FKjx9y24ucmuk0jhifttugnevq6`
        FOREIGN KEY (`post_post_id`)
            REFERENCES `mystory`.`post` (`post_id`),
    CONSTRAINT `FKnxodvna78e7a0mmoftm430v6f`
        FOREIGN KEY (`recommendation_user_key`)
            REFERENCES `mystory`.`user` (`user_key`)
);

CREATE TABLE IF NOT EXISTS `mystory`.`tag`
(
    `tag_id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `tag_data` VARCHAR(15) NOT NULL,
    PRIMARY KEY (`tag_id`)
);

CREATE TABLE IF NOT EXISTS `mystory`.`post_tag`
(
    `post_post_id` BIGINT NOT NULL,
    `tag_tag_id`   BIGINT NOT NULL,
    INDEX `FK3b75g7y6ldgftwxriyqvoul11` (`tag_tag_id` ASC) VISIBLE,
    INDEX `FK92pj99lj917debbgtj0r12pxn` (`post_post_id` ASC) VISIBLE,
    CONSTRAINT `FK3b75g7y6ldgftwxriyqvoul11`
        FOREIGN KEY (`tag_tag_id`)
            REFERENCES `mystory`.`tag` (`tag_id`),
    CONSTRAINT `FK92pj99lj917debbgtj0r12pxn`
        FOREIGN KEY (`post_post_id`)
            REFERENCES `mystory`.`post` (`post_id`)
);

CREATE TABLE IF NOT EXISTS `mystory`.`refresh_token`
(
    `token_id`  BIGINT       NOT NULL AUTO_INCREMENT,
    `key_email` VARCHAR(255) NOT NULL,
    `token`     VARCHAR(255) NOT NULL,
    PRIMARY KEY (`token_id`)
);

CREATE TABLE IF NOT EXISTS `mystory`.`reservation`
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
    INDEX `FKnhnqrre64qdq9tbvp0ggcvnaa` (`meetings_meeting_id` ASC) VISIBLE,
    CONSTRAINT `FKnhnqrre64qdq9tbvp0ggcvnaa`
        FOREIGN KEY (`meetings_meeting_id`)
            REFERENCES `mystory`.`meeting` (`meeting_id`)
);

CREATE TABLE IF NOT EXISTS `mystory`.`reservation_participants`
(
    `reservation_participants_id` BIGINT NOT NULL,
    `reservation_reservation_id`  BIGINT NULL DEFAULT NULL,
    `user_user_key`               BIGINT NULL DEFAULT NULL,
    PRIMARY KEY (`reservation_participants_id`),
    INDEX `FK15lw68nne0c1jcds67ieq1tpw` (`reservation_reservation_id` ASC) VISIBLE,
    INDEX `FKtpy9stvbo5nn6jkvpuxveiw02` (`user_user_key` ASC) VISIBLE,
    CONSTRAINT `FK15lw68nne0c1jcds67ieq1tpw`
        FOREIGN KEY (`reservation_reservation_id`)
            REFERENCES `mystory`.`reservation` (`reservation_id`),
    CONSTRAINT `FKtpy9stvbo5nn6jkvpuxveiw02`
        FOREIGN KEY (`user_user_key`)
            REFERENCES `mystory`.`user` (`user_key`)
);

CREATE TABLE IF NOT EXISTS `mystory`.`reservation_participants_seq`
(
    `next_val` BIGINT NULL DEFAULT NULL
);
