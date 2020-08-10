CREATE SEQUENCE seq_bidder_id
    START WITH 100000
    INCREMENT BY 1
    MINVALUE 100000
    MAXVALUE 9223372036854775807
    NOCACHE;

CREATE TABLE bidder
(
    id                    NUMBER(19)         NOT NULL,
    identification_number VARCHAR2(50 CHAR)  NOT NULL,
    name                  VARCHAR2(100 CHAR) NOT NULL
);

ALTER TABLE bidder
    ADD CONSTRAINT pk_bidder_id PRIMARY KEY (id);

CREATE UNIQUE INDEX uq_bidder_id_no ON bidder (identification_number);