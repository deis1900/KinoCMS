CREATE TABLE IF NOT EXISTS authority
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    role varchar(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS users
(
    id                      BIGINT       NOT NULL AUTO_INCREMENT,
    login                   varchar(255) NOT NULL,
    password                varchar(255) NOT NULL,
    account_Non_Expired     boolean      NOT NULL,
    account_Non_Locked      boolean      NOT NULL,
    credentials_Non_Expired boolean      NOT NULL,
    enabled                 boolean      NOT NULL,
    token                   varchar(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS user_authority
(
    user_id      BIGINT NOT NULL,
    authority_id BIGINT NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS customers
(
    customer_id BIGINT       NOT NULL AUTO_INCREMENT,
    login       varchar(255) NOT NULL,
    PRIMARY KEY (customer_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS customer_details
(
    customer_id  BIGINT NOT NULL AUTO_INCREMENT,
    birthday     DATE,
    city         varchar(255),
    language     int,
    registration TIMESTAMP,
    sex          varchar(255),
    registr_date DATETIME,
    PRIMARY KEY (customer_id),
    CONSTRAINT `fk_customers_details` FOREIGN KEY (customer_id) REFERENCES customers (customer_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS customer_contacts
(
    customer_id BIGINT       NOT NULL AUTO_INCREMENT,
    address     varchar(255),
    card        varchar(255),
    email       varchar(255) NOT NULL,
    name        varchar(255),
    phone       varchar(255),
    surname     varchar(255),
    PRIMARY KEY (customer_id),
    CONSTRAINT `fk_customers_contacts` FOREIGN KEY (customer_id) REFERENCES customers (customer_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS films
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    duration    time         DEFAULT NULL,
    finish_date datetime     DEFAULT NULL,
    name        varchar(255) DEFAULT NULL,
    start_date  datetime     DEFAULT NULL,
    user_age    int(11)      DEFAULT NULL,
    video_type  varchar(255) DEFAULT NULL,
    quality     int(11)      DEFAULT NULL,
    main_photo  varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS films_details
(
    actors     varchar(255) DEFAULT NULL,
    budget     int(11)      DEFAULT NULL,
    compositor varchar(255) DEFAULT NULL,
    director   varchar(255) DEFAULT NULL,
    genres     varchar(255) DEFAULT NULL,
    info       text,
    producer   varchar(255) DEFAULT NULL,
    scenarist  varchar(255) DEFAULT NULL,
    trailer    varchar(255) DEFAULT NULL,
    id         bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT `fk_film_detailss` FOREIGN KEY (id) REFERENCES films (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS seat
(
    id     BIGINT NOT NULL AUTO_INCREMENT,
    series int,
    place  int,
    free   boolean,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS booking
(
    id          BIGINT   NOT NULL AUTO_INCREMENT,
    create_date DATETIME NOT NULL,
    pay         boolean,
    customer_id BIGINT   NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_customers_booking FOREIGN KEY (id) REFERENCES customers (customer_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS ticket
(
    `id`         BIGINT NOT NULL AUTO_INCREMENT,
    `price`      int    NOT NULL,
    `session_id` bigint NOT NULL,
    `booking_id` bigint NOT NULL,
    `seat_id`    bigint NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_booking_ticket` FOREIGN KEY (`booking_id`) REFERENCES booking (`id`),
    CONSTRAINT `fk_seat_ticket` FOREIGN KEY (`seat_id`) REFERENCES seat (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `photos`
(
    `id`      BIGINT NOT NULL AUTO_INCREMENT,
    `name`    varchar(255) DEFAULT NULL,
    `type`    varchar(255) DEFAULT NULL,
    `url`     varchar(255) DEFAULT NULL,
    `film_id` BIGINT       DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FKtrp5tthblqudcutv31yyb23oi` FOREIGN KEY (`film_id`) REFERENCES films (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `sessions`
(
    `id`        BIGINT   NOT NULL AUTO_INCREMENT,
    `show_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `film_id`   bigint            DEFAULT NULL,
    `room_id`   bigint            DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_films_sessions` FOREIGN KEY (film_id) REFERENCES films (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
