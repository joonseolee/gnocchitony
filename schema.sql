CREATE TABLE onlineuser
(
    id       INT         NOT NULL PRIMARY KEY IDENTITY (1,1),
    email    VARCHAR(55) NOT NULL,
    onlineid VARCHAR(55) NOT NULL,
    fullname VARCHAR(55) NOT NULL,
)

CREATE TABLE committee
(
    id   INT         NOT NULL PRIMARY KEY IDENTITY (1,1),
    name VARCHAR(55) NOT NULL,

);



CREATE TABLE reciept
(
    id            INT            NOT NULL PRIMARY KEY IDENTITY (1,1),
    amount        DECIMAL(10, 2) NOT NULL,
    comitee_id    INT            NOT NULL,
    name          VARCHAR(100)   NOT NULL,
    description   TEXT           NOT NULL,
    onlineuser_id INT            NOT NULL references onlineuser (id),
);

CREATE TABLE attachment
(
    id         INT            NOT NULL PRIMARY KEY IDENTITY (1,1),
    img        varbinary(MAX) NOT NULL,
    reciept_id INT            NOT NULL references reciept (id),
);


CREATE TABLE payment
(
    id             INT         NOT NULL PRIMARY KEY IDENTITY (1,1),
    reciept_id     INT         NOT NULL references reciept (id),
    account_number VARCHAR(55) NOT NULL,

);

CREATE TABLE card
(
    id          INT         NOT NULL PRIMARY KEY IDENTITY (1,1),
    reciept_id  INT         NOT NULL references reciept (id),
    card_number VARCHAR(16) NOT NULL,

);

CREATE TABLE economicrequest
(
    id                 INT            NOT NULL PRIMARY KEY IDENTITY (1,1),
    subject            VARCHAR(55)    NOT NULL,
    purpose            TEXT           NOT NULL,
    date               DATE           NOT NULL,
    duration           VARCHAR(255)   NOT NULL,
    description        TEXT           NOT NULL,
    amount             DECIMAL(10, 2) NOT NULL,
    personcount        INT            NOT NULL,
    names              varchar(max)   NOT NULL,
    paymentdescription varchar(255)   NOT NULL,
    otherinformation   varchar(max)   NOT NULL,
    createdat          DATETIME       NOT NULL,
    onlineuser_id      INT            NOT NULL references onlineuser (id),
);
