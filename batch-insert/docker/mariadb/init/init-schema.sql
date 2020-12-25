-- Autogenerated: do not edit this file
CREATE TABLE IF NOT EXISTS stat
(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS stat_detail
(
    seq     BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    score   BIGINT NOT NULL,
    stat_id BIGINT NOT NULL,
    CONSTRAINT FK_STAT_DETAIL FOREIGN KEY (stat_id) REFERENCES stat (ID)
) ENGINE = InnoDB;

CREATE TABLE stat_sequence
(
    sequence_name varchar(255) NOT NULL PRIMARY KEY COMMENT '시퀀스 이름',
    next_val      bigint       NOT NULL COMMENT '시퀀스 값'
) ENGINE = InnoDB COMMENT ='시퀀스 테이블';
