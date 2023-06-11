create table profile
(
    profile_key bigint  not null AUTO_INCREMENT,
    email       varchar(255),
    options      integer not null,
    phone       varchar(255),
    primary key (profile_key)
);

create table user
(
    user_key            bigint       not null AUTO_INCREMENT,
    id                  varchar(20)  not null,
    join_date           date         not null,
    password            varchar(255)  not null,
    role                varchar(255) not null,
    user_type           varchar(255) not null,
    profile_profile_key bigint       not null,
    primary key (user_key),
    foreign key (profile_profile_key) references profile (profile_key)
);

create table post
(
    post_id          bigint       not null auto_increment,
    content          varchar(255) not null,
    is_block_comment varchar(255) not null,
    is_private       varchar(255) not null,
    likes            integer      not null,
    post_date        datetime(6),
    title            varchar(30)  not null,
    views            integer      not null,
    writer_user_key  bigint,
    primary key (post_id),
    foreign key (writer_user_key) references user (user_key)
);
create fulltext index idx_post_title on post(title);
create fulltext index idx_post_content on post(content);

create table attachment
(
    attachment_id  bigint       not null AUTO_INCREMENT,
    file_size      bigint       not null,
    real_file_name varchar(30)  not null,
    s3url          varchar(100) not null,
    uuid_file_name varchar(40)  not null,
    post_post_id   bigint       not null,
    primary key (attachment_id) ,
    foreign key (post_post_id) references post (post_id)
);

create table comment
(
    comment_id      bigint       not null AUTO_INCREMENT,
    content         varchar(200) not null,
    post_date       datetime(6),
    post_post_id    bigint       not null,
    writer_user_key bigint,
    primary key (comment_id) ,
    foreign key (post_post_id) references post (post_id) ,
    foreign key (writer_user_key) references user (user_key)
);

create table post_recommendation
(
    post_post_id            bigint not null,
    recommendation_user_key bigint not null,
    unique (recommendation_user_key , post_post_id),
    foreign key (recommendation_user_key) references user (user_key),
    foreign key (post_post_id) references post (post_id)
);

create table tag
(
    tag_id   bigint      not null AUTO_INCREMENT,
    tag_data varchar(15) not null,
    primary key (tag_id)
);

create table post_tag
(
    post_post_id bigint not null,
    tag_tag_id   bigint not null,
    foreign key (tag_tag_id) references tag (tag_id),
    foreign key (post_post_id) references post (post_id)
);

create table refresh_token
(
    token_id bigint       not null auto_increment,
    key_email        varchar(255) not null,
    token    varchar(255) not null,
    primary key (token_id)
);