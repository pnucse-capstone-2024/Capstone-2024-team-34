CREATE DATABASE IF NOT EXISTS cartalk;

CREATE TABLE IF NOT EXISTS chatroom
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime              NULL,
    updated_at datetime              NULL,
    title      VARCHAR(50)           NOT NULL,
    user_id    BIGINT                NULL,
    CONSTRAINT pk_chatroom PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS email_verification
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    created_at        datetime              NULL,
    updated_at        datetime              NULL,
    email             VARCHAR(100)          NOT NULL,
    verification_code VARCHAR(10)           NOT NULL,
    CONSTRAINT pk_email_verification PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS favor
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime              NULL,
    updated_at datetime              NULL,
    user_id    BIGINT                NULL,
    vehicle_id BIGINT                NULL,
    CONSTRAINT pk_favor PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS message_tb
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime              NULL,
    updated_at      datetime              NULL,
    chatroom_id     BIGINT                NULL,
    is_from_chatbot BIT(1)                NOT NULL,
    content         TEXT                  NOT NULL,
    uuid            VARCHAR(255)          NULL,
    CONSTRAINT pk_message_tb PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users
(
    id         BIGINT AUTO_INCREMENT              NOT NULL,
    created_at datetime                           NULL,
    updated_at datetime                           NULL,
    login_id   VARCHAR(40)                        NOT NULL,
    password   VARCHAR(100)                       NOT NULL,
    name       VARCHAR(40)          NOT NULL,
    gender     BIT(1)      DEFAULT 0              NULL,
    birth      date        DEFAULT '2000-01-01'   NOT NULL,
    email      VARCHAR(100)                       NOT NULL,
    `role`     VARCHAR(50) DEFAULT 'ROLE_PENDING' NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS vehicle
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime              NULL,
    updated_at datetime              NULL,
    link       VARCHAR(100)          NOT NULL,
    name       VARCHAR(200)          NOT NULL,
    price      INT                   NOT NULL,
    fuel       VARCHAR(50)           NOT NULL,
    mileage    INT                   NOT NULL,
    years      VARCHAR(50)           NOT NULL,
    brand      VARCHAR(50)           NOT NULL,
    image_url  VARCHAR(100)          NOT NULL,
    CONSTRAINT pk_vehicle PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_users_loginid UNIQUE (login_id);

CREATE INDEX email_verification_email_idx ON email_verification (email);

CREATE INDEX users_email_idx ON users (email);

CREATE INDEX users_loginId_idx ON users (login_id);

ALTER TABLE chatroom
    ADD CONSTRAINT FK_CHATROOM_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

CREATE INDEX chatroom_user_id_idx ON chatroom (user_id);

ALTER TABLE favor
    ADD CONSTRAINT FK_FAVOR_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

CREATE INDEX favor_user_id_idx ON favor (user_id);

ALTER TABLE favor
    ADD CONSTRAINT FK_FAVOR_ON_VEHICLE FOREIGN KEY (vehicle_id) REFERENCES vehicle (id);

ALTER TABLE message_tb
    ADD CONSTRAINT FK_MESSAGE_TB_ON_CHATROOM FOREIGN KEY (chatroom_id) REFERENCES chatroom (id);

CREATE INDEX message_chatroom_id_idx ON message_tb (chatroom_id);

ALTER DATABASE your_database_name CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci;

SELECT CONCAT('ALTER TABLE ', TABLE_NAME, ' CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;')
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = 'cartalk';

ALTER TABLE chatroom
MODIFY title VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

ALTER TABLE message_tb
MODIFY content TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

ALTER TABLE vehicle
MODIFY name VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
MODIFY fuel VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
MODIFY brand VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
MODIFY image_url VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
