CREATE SEQUENCE seq_issuer_id
    START WITH 100000
    INCREMENT BY 1
    MINVALUE 100000
    MAXVALUE 9223372036854775807
    NOCACHE;

CREATE TABLE issuer
(
    id                    NUMBER(19)         NOT NULL,
    identification_number VARCHAR2(50 CHAR)  NOT NULL,
    name                  VARCHAR2(100 CHAR) NOT NULL
);

ALTER TABLE issuer
    ADD CONSTRAINT pk_issuer_id PRIMARY KEY (id);

CREATE UNIQUE INDEX uq_issuer_id_no ON issuer (identification_number);