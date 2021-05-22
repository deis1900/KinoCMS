CREATE TABLE IF NOT EXISTS `cinema`
(
    `id`         BIGINT NOT NULL AUTO_INCREMENT,
    `main_photo` varchar(255) DEFAULT NULL,
    `name`       varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `cinema_info`
(
    `address`  varchar(255) DEFAULT NULL,
    `info`     text,
    `location` varchar(255) DEFAULT NULL,
    `phone`    varchar(255) DEFAULT NULL,
    `id`       BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK21tvngua1805y8v2s6ieoafx8` FOREIGN KEY (`id`) REFERENCES `cinema` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;



CREATE TABLE IF NOT EXISTS `news`
(
    `id`          BIGINT NOT NULL AUTO_INCREMENT,
    `finish_time` datetime DEFAULT NULL,
    `info`        text,
    `newsType`    int(11)  DEFAULT NULL,
    `start_time`  datetime DEFAULT NULL,
    `state`       bit(1)   DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `rooms`
(
    `room_id`      BIGINT   NOT NULL AUTO_INCREMENT,
    `banner`       varchar(255) DEFAULT NULL,
    `info`         text,
    `name`         varchar(255) NOT NULL,
    `seat_place`   int(11)      DEFAULT NULL,
    `room_schema`  varchar(255) DEFAULT NULL,
    `place_series` int(11)      DEFAULT NULL,
    `cinema_id`    BIGINT   DEFAULT NULL,
    PRIMARY KEY (`room_id`),
    KEY `FKtrp5tthblqudcutv31yyb21hq` (`cinema_id`),
    CONSTRAINT `FKtrp5tthblqudcutv31yyb21hq` FOREIGN KEY (`cinema_id`) REFERENCES `cinema` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `images`
(
    `id`        BIGINT NOT NULL AUTO_INCREMENT,
    `name`      varchar(255) DEFAULT NULL,
    `type`      varchar(255) DEFAULT NULL,
    `url`       varchar(255) DEFAULT NULL,
    `room_id`   BIGINT   DEFAULT NULL,
    `film_id`   BIGINT   DEFAULT NULL,
    `news_id`   BIGINT   DEFAULT NULL,
    `cinema_id` BIGINT   DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK42b7tvpt3xa59rpn8u9f0uy2o` (`room_id`),
    KEY `FK416fa3a7dc4n8smru9r2yq7u8` (`news_id`),
    KEY `FKrooyvwgxhphv6ngaj4hlpq5w4` (`cinema_id`),
    CONSTRAINT `FK416fa3a7dc4n8smru9r2yq7u8` FOREIGN KEY (`news_id`) REFERENCES `news` (`id`),
    CONSTRAINT `FK42b7tvpt3xa59rpn8u9f0uy2o` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`),
    CONSTRAINT `FKrooyvwgxhphv6ngaj4hlpq5w4` FOREIGN KEY (`cinema_id`) REFERENCES `cinema` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
