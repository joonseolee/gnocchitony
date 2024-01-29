DROP TABLE IF EXISTS attachment;
DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS card;
DROP TABLE IF EXISTS reciept;
DROP TABLE IF EXISTS onlineuser;
DROP TABLE IF EXISTS committee;


CREATE TABLE committee (
        id INT NOT NULL PRIMARY KEY IDENTITY(1,1),
        name VARCHAR(55) NOT NULL

);


CREATE TABLE onlineuser (
        id INT NOT NULL PRIMARY KEY IDENTITY(1,1),
        email VARCHAR(55) NOT NULL,
        online_id VARCHAR(55) NOT NULL,

);


CREATE TABLE reciept (
     id INT NOT NULL PRIMARY KEY IDENTITY(1,1),
     amount DECIMAL(10,2) NOT NULL,
     comitee_id INT NOT NULL,
     name VARCHAR(100) NOT NULL,
     description varbinary(max) NOT NULL,
     onlineuser_id INT NOT NULL,

);

CREATE TABLE attachment (
                            id INT NOT NULL PRIMARY KEY IDENTITY(1,1),
                            img varbinary(max) NOT NULL,
                            reciept_id INT NOT NULL,

);


CREATE TABLE payment (
                         id INT NOT NULL PRIMARY KEY IDENTITY(1,1),
                         reciept_id INT NOT NULL,
                         account_number VARCHAR(55) NOT NULL,

);


CREATE TABLE card (
                      id INT NOT NULL PRIMARY KEY IDENTITY(1,1),
                      reciept_id INT NOT NULL,
                      card_number VARCHAR(16) NOT NULL,

);

ALTER TABLE reciept ADD CONSTRAINT reciept_fk0 FOREIGN KEY (comitee_id) REFERENCES committee(id);

ALTER TABLE reciept ADD CONSTRAINT reciept_fk1 FOREIGN KEY (onlineuser_id) REFERENCES onlineuser(id);

ALTER TABLE attachment ADD CONSTRAINT attachment_fk0 FOREIGN KEY (reciept_id) REFERENCES reciept(id);

ALTER TABLE payment ADD CONSTRAINT payment_fk0 FOREIGN KEY (reciept_id) REFERENCES reciept(id);

ALTER TABLE card ADD CONSTRAINT card_fk0 FOREIGN KEY (reciept_id) REFERENCES reciept(id);