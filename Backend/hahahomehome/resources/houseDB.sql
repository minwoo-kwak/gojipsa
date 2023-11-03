
CREATE DATABASE housedb;
use housedb;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    user_id    VARCHAR(20)    NOT NULL PRIMARY KEY,
    password    VARCHAR(20)    NOT NULL,
    name    VARCHAR(10)    NOT NULL,
    grad    VARCHAR(10)    NOT NULL
);

DROP TABLE IF EXISTS wishlist;

CREATE TABLE wishlist (
    wishlist_id    int    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id    VARCHAR(20)    NOT NULL,
    aptCode    bigint    NOT NULL
);

DROP TABLE IF EXISTS board;

CREATE TABLE board (
    board_no    int    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title    VARCHAR(100)    NOT NULL,
    content    VARCHAR(2000)    NOT NULL,
    register_time    timestamp    NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    hit    int    NOT NULL    DEFAULT 0,
    user_id    VARCHAR(20)    NOT NULL
);

DROP TABLE IF EXISTS dongcode;

CREATE TABLE dongcode (
    dongCode    varchar(10)    NOT NULL PRIMARY KEY,
    sidoName    varchar(30)    NULL,
    gugunName    varchar(30)    NULL,
    dongName    varchar(30)    NULL
);

DROP TABLE IF EXISTS housedeal;

CREATE TABLE housedeal (
    no    bigint    NOT NULL PRIMARY KEY,
    dealAmount    varchar(20)    NULL,
    dealYear    int    NULL,
    dealMonth    int    NULL,
    dealDay    int    NULL,
    area    varchar(20)    NULL,
    floor    varchar(4)    NULL,
    cancelDealType    varchar(1)    NULL,
    aptCode    bigint    NOT NULL
);

DROP TABLE IF EXISTS houseinfo;

CREATE TABLE houseinfo (
    aptCode    bigint    NOT NULL PRIMARY KEY,
    buildYear    int    NULL,
    roadName    varchar(40)    NULL,
    roadNameBonbun    varchar(5)    NULL,
    roadNameBubun    varchar(5)    NULL,
    roadNameSeq    varchar(2)    NULL,
    roadNameBasementCode    varchar(1)    NULL,
    roadNameCode    varchar(7)    NULL,
    dong    varchar(40)    NULL,
    bonbun    varchar(4)    NULL,
    bubun    varchar(4)    NULL,
    sigunguCode    varchar(5)    NULL,
    eubmyundongCode    varchar(5)    NULL,
    landCode    varchar(1)    NULL,
    apartmentName    varchar(40)    NULL,
    jibun    varchar(10)    NULL,
    lng    varchar(30)    NULL,
    lat    varchar(30)    NULL,
    dongCode    varchar(10)    NOT NULL
);




ALTER TABLE wishlist ADD CONSTRAINT FK_user_TO_wishlist_1 FOREIGN KEY (
    user_id
)
REFERENCES user (
    user_id
);

ALTER TABLE wishlist ADD CONSTRAINT FK_houseinfo_TO_wishlist_1 FOREIGN KEY (
    aptCode
)
REFERENCES houseinfo (
    aptCode
);

ALTER TABLE board ADD CONSTRAINT FK_user_TO_board_1 FOREIGN KEY (
    user_id
)
REFERENCES user (
    user_id
);

ALTER TABLE housedeal ADD CONSTRAINT FK_houseinfo_TO_housedeal_1 FOREIGN KEY (
    aptCode
)
REFERENCES houseinfo (
    aptCode
);

ALTER TABLE houseinfo ADD CONSTRAINT FK_dongcode_TO_houseinfo_1 FOREIGN KEY (
    dongCode
)
REFERENCES dongcode (
    dongCode
);

INSERT INTO user VALUES ('ssafy', 'ssafy', '김싸피', '관리자');
INSERT INTO user VALUES('user','user','김유저','사용자');

INSERT INTO `housedb`.`board` ( `title`, `content`, `hit`, `user_id`) VALUES ('제목', '내용', '0', 'ssafy');