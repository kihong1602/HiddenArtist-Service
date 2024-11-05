CREATE TABLE account
(
    id            bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    email         varchar(255) UNIQUE,
    password      varchar(255),
    nickname      varchar(255),
    profile_image varchar(255),
    role          varchar(255)                DEFAULT 'USER',
    provider_type varchar(255)                DEFAULT 'LOCAL',
    provider_id   varchar(255) unique,
    create_date   datetime           NOT NULL DEFAULT now(),
    update_date   datetime           NOT NULL DEFAULT now(),
    delete_date   datetime
);

CREATE TABLE artist
(
    id            bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name          varchar(255),
    birth         date,
    summary       varchar(255),
    description   varchar(255),
    profile_image varchar(255),
    token         varchar(255) UNIQUE,
    create_date   datetime           NOT NULL DEFAULT now(),
    update_date   datetime           NOT NULL DEFAULT now(),
    delete_date   datetime
);

CREATE TABLE artist_contact
(
    id            bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    artist_id     bigint,
    type          varchar(255),
    label         varchar(255),
    contact_value varchar(255),
    create_date   datetime           NOT NULL DEFAULT now(),
    update_date   datetime           NOT NULL DEFAULT now(),
    delete_date   datetime
);

CREATE TABLE artwork
(
    id                bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name              varchar(255),
    image             varchar(255),
    description       varchar(255),
    production_year   date,
    width             double,
    height            double,
    depth             double,
    token             varchar(255) UNIQUE,
    artwork_medium_id bigint,
    create_date       datetime           NOT NULL DEFAULT now(),
    update_date       datetime           NOT NULL DEFAULT now(),
    delete_date       datetime
);

CREATE TABLE artwork_medium
(
    id          bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    type_name   varchar(255) UNIQUE,
    create_date datetime           NOT NULL DEFAULT now(),
    update_date datetime           NOT NULL DEFAULT now(),
    delete_date datetime
);

CREATE TABLE signature_artwork
(
    id            bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    artwork_id    bigint,
    display_order tinyint,
    create_date   datetime           NOT NULL DEFAULT now(),
    update_date   datetime           NOT NULL DEFAULT now(),
    delete_date   datetime
);

CREATE TABLE artist_artwork
(
    id          bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    artist_id   bigint,
    artwork_id  bigint,
    create_date datetime           NOT NULL DEFAULT now(),
    update_date datetime           NOT NULL DEFAULT now(),
    delete_date datetime
);

CREATE TABLE artist_genre
(
    id          bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    artist_id   bigint,
    genre_id    bigint,
    create_date datetime           NOT NULL DEFAULT now(),
    update_date datetime           NOT NULL DEFAULT now(),
    delete_date datetime
);

CREATE TABLE artwork_genre
(
    id          bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    artwork_id  bigint,
    genre_id    bigint,
    create_date datetime           NOT NULL DEFAULT now(),
    update_date datetime           NOT NULL DEFAULT now(),
    delete_date datetime
);

CREATE TABLE genre
(
    id          bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name        varchar(255) UNIQUE,
    create_date datetime           NOT NULL DEFAULT now(),
    update_date datetime           NOT NULL DEFAULT now(),
    delete_date datetime
);

CREATE TABLE exhibition
(
    id            bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name          varchar(255),
    image         varchar(255),
    description   mediumtext,
    start_date    date,
    end_date      date,
    open_time     time,
    close_time    time,
    closed_days   varchar(255),
    admission_fee integer,
    token         varchar(255) UNIQUE,
    location_id   bigint,
    manager_id    bigint,
    create_date   datetime           NOT NULL DEFAULT now(),
    update_date   datetime           NOT NULL DEFAULT now(),
    delete_date   datetime
);

CREATE TABLE exhibition_location
(
    id          bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name        varchar(255),
    address     varchar(255),
    latitude    double,
    longitude   double,
    create_date datetime           NOT NULL DEFAULT now(),
    update_date datetime           NOT NULL DEFAULT now(),
    delete_date datetime
);

CREATE TABLE exhibition_manager
(
    id          bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    email       varchar(255),
    tel         varchar(255),
    create_date datetime           NOT NULL DEFAULT now(),
    update_date datetime           NOT NULL DEFAULT now(),
    delete_date datetime
);

CREATE TABLE mentor
(
    id                   bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    summary              varchar(255),
    organization         varchar(255),
    career               enum ('JUNIOR','MIDDLE','SENIOR','LEADER'),
    bank_name            varchar(255),
    account_name         varchar(255),
    account_number       varchar(255),
    certification_status enum ('UNVERIFIED','VERIFIED'),
    contact_email        varchar(255),
    account_id           bigint,
    create_date          datetime           NOT NULL DEFAULT now(),
    update_date          datetime           NOT NULL DEFAULT now(),
    delete_date          datetime
);

CREATE TABLE mentor_genre
(
    id          bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    mentor_id   bigint,
    genre_id    bigint,
    create_date datetime           NOT NULL DEFAULT now(),
    update_date datetime           NOT NULL DEFAULT now(),
    delete_date datetime
);

CREATE TABLE mentoring
(
    id                      bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name                    varchar(255),
    image                   varchar(255),
    content                 mediumtext,
    duration_time           varchar(255),
    amount                  integer,
    total_application_count bigint,
    mentoring_status        enum ('OPEN','CLOSED'),
    token                   varchar(255) UNIQUE,
    mentor_id               bigint,
    create_date             datetime           NOT NULL DEFAULT now(),
    update_date             datetime           NOT NULL DEFAULT now(),
    delete_date             datetime
);

CREATE TABLE mentoring_genre
(
    id           bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    mentoring_id bigint,
    genre_id     bigint,
    create_date  datetime           NOT NULL DEFAULT now(),
    update_date  datetime           NOT NULL DEFAULT now(),
    delete_date  datetime
);

CREATE TABLE mentoring_application
(
    id                           bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    mentoring_id                 bigint,
    account_id                   bigint,
    token                        varchar(255),
    application_time             datetime,
    mentoring_application_status enum ('WAITING','CANCELLED','APPROVAL','COMPLETE'),
    create_date                  datetime           NOT NULL DEFAULT now(),
    update_date                  datetime           NOT NULL DEFAULT now(),
    delete_date                  datetime
);

CREATE TABLE mentoring_review
(
    id                       bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    content                  varchar(255),
    rating                   integer,
    mentoring_application_id bigint,
    mentoring_id             bigint,
    create_date              datetime           NOT NULL DEFAULT now(),
    update_date              datetime           NOT NULL DEFAULT now(),
    delete_date              datetime
);

CREATE TABLE payment
(
    id                       bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    order_id                 varchar(255),
    client_order_id          varchar(255),
    amount                   integer,
    cancel_amount            integer,
    payment_status           enum ('SUCCESS','CANCELLED'),
    mentoring_application_id bigint,
    create_date              datetime           NOT NULL DEFAULT now(),
    update_date              datetime           NOT NULL DEFAULT now(),
    delete_date              datetime
);

CREATE TABLE settlement
(
    id                bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    settlement_date   datetime,
    settlement_status enum ('WAITING','COMPLETE'),
    mentor_id         bigint,
    payment_id        bigint,
    create_date       datetime           NOT NULL DEFAULT now(),
    update_date       datetime           NOT NULL DEFAULT now(),
    delete_date       datetime
);

CREATE TABLE follow_artist
(
    id          bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    artist_id   bigint,
    account_id  bigint,
    create_date datetime           NOT NULL DEFAULT now(),
    update_date datetime           NOT NULL DEFAULT now(),
    delete_date datetime
);

CREATE TABLE pick_artwork
(
    id          bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
    artwork_id  bigint,
    account_id  bigint,
    create_date datetime           NOT NULL DEFAULT now(),
    update_date datetime           NOT NULL DEFAULT now(),
    delete_date datetime
);

ALTER TABLE artist_contact
    ADD FOREIGN KEY (artist_id) REFERENCES artist (id);

ALTER TABLE artwork
    ADD FOREIGN KEY (artwork_medium_id) REFERENCES artwork_medium (id);

ALTER TABLE signature_artwork
    ADD FOREIGN KEY (artwork_id) REFERENCES artwork (id);

ALTER TABLE artist_artwork
    ADD FOREIGN KEY (artist_id) REFERENCES artist (id);

ALTER TABLE artist_artwork
    ADD FOREIGN KEY (artwork_id) REFERENCES artwork (id);

ALTER TABLE artist_genre
    ADD FOREIGN KEY (artist_id) REFERENCES artist (id);

ALTER TABLE artist_genre
    ADD FOREIGN KEY (genre_id) REFERENCES genre (id);

ALTER TABLE artwork_genre
    ADD FOREIGN KEY (artwork_id) REFERENCES artwork (id);

ALTER TABLE artwork_genre
    ADD FOREIGN KEY (genre_id) REFERENCES genre (id);

ALTER TABLE exhibition
    ADD FOREIGN KEY (location_id) REFERENCES exhibition_location (id);

ALTER TABLE exhibition
    ADD FOREIGN KEY (manager_id) REFERENCES exhibition_manager (id);

ALTER TABLE mentor
    ADD FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE mentor_genre
    ADD FOREIGN KEY (mentor_id) REFERENCES mentor (id);

ALTER TABLE mentor_genre
    ADD FOREIGN KEY (genre_id) REFERENCES genre (id);

ALTER TABLE mentoring
    ADD FOREIGN KEY (mentor_id) REFERENCES mentor (id);

ALTER TABLE mentoring_genre
    ADD FOREIGN KEY (mentoring_id) REFERENCES mentoring (id);

ALTER TABLE mentoring_genre
    ADD FOREIGN KEY (genre_id) REFERENCES genre (id);

ALTER TABLE mentoring_application
    ADD FOREIGN KEY (mentoring_id) REFERENCES mentoring (id);

ALTER TABLE mentoring_application
    ADD FOREIGN KEY (account_id) REFERENCES payment (id);

ALTER TABLE mentoring_review
    ADD FOREIGN KEY (mentoring_application_id) REFERENCES mentoring_application (id);

ALTER TABLE mentoring_review
    ADD FOREIGN KEY (mentoring_id) REFERENCES mentoring (id);

ALTER TABLE payment
    Add FOREIGN KEY (mentoring_application_id) REFERENCES mentoring_application (id);

ALTER TABLE settlement
    ADD FOREIGN KEY (mentor_id) REFERENCES mentor (id);

ALTER TABLE settlement
    ADD FOREIGN KEY (payment_id) REFERENCES payment (id);

ALTER TABLE follow_artist
    ADD FOREIGN KEY (artist_id) REFERENCES artist (id);

ALTER TABLE follow_artist
    ADD FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE pick_artwork
    ADD FOREIGN KEY (artwork_id) REFERENCES artwork (id);

ALTER TABLE pick_artwork
    ADD FOREIGN KEY (account_id) REFERENCES account (id);

CREATE FULLTEXT INDEX artist_name_idx ON artist (name) WITH PARSER ngram;

CREATE FULLTEXT INDEX artwork_name_idx ON artwork (name) WITH PARSER ngram;

CREATE FULLTEXT INDEX genre_name_idx ON genre (name) WITH PARSER ngram;

CREATE FULLTEXT INDEX exhibition_name_idx ON exhibition (name) WITH PARSER ngram;

CREATE FULLTEXT INDEX mentoring_name_idx ON mentoring (name) WITH PARSER ngram;
