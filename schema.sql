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

CREATE TABLE receipt
(
    id            INT            NOT NULL PRIMARY KEY IDENTITY (1,1),
    amount        DECIMAL(10, 2) NOT NULL,
    committee_id  INT            NOT NULL references committee (id),
    name          VARCHAR(255)   NOT NULL,
    description   VARCHAR(500)   NOT NULL,
    createdat     DATETIME       NOT NULL,
    onlineuser_id INT            NOT NULL references onlineuser (id),
);

CREATE TABLE attachment
(
    id         INT          NOT NULL PRIMARY KEY IDENTITY (1,1),
    receipt_id INT          NOT NULL references receipt (id),
    name       VARCHAR(255) NOT NULL,
);


CREATE TABLE payment
(
    id             INT         NOT NULL PRIMARY KEY IDENTITY (1,1),
    receipt_id     INT         NOT NULL references receipt (id),
    account_number VARCHAR(55) NOT NULL,

);

CREATE TABLE card
(
    id          INT         NOT NULL PRIMARY KEY IDENTITY (1,1),
    receipt_id  INT         NOT NULL references receipt (id),
    card_number VARCHAR(16) NOT NULL,

);

CREATE TABLE economicrequest
(
    id                 INT            NOT NULL PRIMARY KEY IDENTITY (1,1),
    subject            VARCHAR(55)    NOT NULL,
    purpose            VARCHAR(500)   NOT NULL,
    date               DATE           NOT NULL,
    duration           VARCHAR(255)   NOT NULL,
    description        VARCHAR(500)   NOT NULL,
    amount             DECIMAL(10, 2) NOT NULL,
    personcount        INT            NOT NULL,
    names              VARCHAR(500)   NOT NULL,
    paymentdescription varchar(255)   NOT NULL,
    otherinformation   VARCHAR(500)   NOT NULL,
    createdat          DATETIME       NOT NULL,
    onlineuser_id      INT            NOT NULL references onlineuser (id),
);

CREATE TABLE receiptreview
(
    id            INT          NOT NULL PRIMARY KEY IDENTITY (1,1),
    receipt_id    INT          NOT NULL references receipt (id),
    status        VARCHAR(20)  NOT NULL CHECK (status IN ('APPROVED', 'DENIED')),
    comment       VARCHAR(500) NOT NULL,
    createdat     DATETIME     NOT NULL,
    onlineuser_id INT          NOT NULL references onlineuser (id),
);

CREATE VIEW receipt_info AS
SELECT r.id               AS receipt_id,
       r.amount,
       MAX(r.name)        AS receipt_name,
       MAX(r.description) AS receipt_description,
       MAX(r.createdat)   AS receipt_created_at,
       MAX(c.name)        AS committee_name,
       MAX(ou.fullname)   AS user_fullname,
       MAX(ou.id)         AS user_id,

       CASE
           WHEN MAX(p.id) IS NOT NULL THEN 'Payment'
           WHEN MAX(ca.id) IS NOT NULL THEN 'Card'
           ELSE 'None'
           END            AS payment_or_card,

       COUNT(a.id)        AS attachment_count,

       MAX(rr.status)     AS latest_review_status,
       MAX(rr.createdat)  AS latest_review_created_at,
       MAX(rr.comment)    AS latest_review_comment

FROM receipt r
         LEFT JOIN committee c ON r.committee_id = c.id
         LEFT JOIN onlineuser ou ON r.onlineuser_id = ou.id
         LEFT JOIN payment p ON r.id = p.receipt_id
         LEFT JOIN card ca ON r.id = ca.receipt_id
         LEFT JOIN attachment a ON r.id = a.receipt_id
         LEFT JOIN receiptreview rr ON r.id = rr.receipt_id
         LEFT JOIN (
    SELECT receipt_id, MAX(createdat) AS max_createdat
    FROM receiptreview
    GROUP BY receipt_id
) latest_review ON rr.receipt_id = latest_review.receipt_id
    AND rr.createdat = latest_review.max_createdat

GROUP BY r.id, r.amount;

