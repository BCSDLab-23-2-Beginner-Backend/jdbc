DROP DATABASE community;
CREATE DATABASE community;
use community;
-- 유저 테이블
CREATE TABLE users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 카테고리 테이블
CREATE TABLE categories (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(255) NOT NULL
);

-- 해시태그 테이블
CREATE TABLE hashtags (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255) NOT NULL
);

-- 게시글 테이블
CREATE TABLE board (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       user_id INT,
                       category_id INT,
                       title VARCHAR(255) NOT NULL,
                       content TEXT,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                       FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

-- 게시글과 해시태그의 관계를 나타내는 연결 테이블
CREATE TABLE board_hashtags (
                                board_id INT,
                                hashtag_id INT,
                                PRIMARY KEY (board_id, hashtag_id),
                                FOREIGN KEY (board_id) REFERENCES board(id) ON DELETE CASCADE,
                                FOREIGN KEY (hashtag_id) REFERENCES hashtags(id) ON DELETE CASCADE
);

select * from users;
delete from users;
alter table users AUTO_INCREMENT = 1;

-- 추가 부분
ALTER DATABASE community CHARACTER SET utf8 COLLATE utf8_general_ci;
alter table users convert to character set utf8 collate utf8_general_ci;
alter table board convert to character set utf8 collate utf8_general_ci;
alter table hashtags convert to character set utf8 collate utf8_general_ci;
alter table categories convert to character set utf8 collate utf8_general_ci;
alter table board_hashtags convert to character set utf8 collate utf8_general_ci;