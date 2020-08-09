CREATE SEQUENCE seq_tender_id
    START WITH 100000
    INCREMENT BY 1
    MINVALUE 100000
    MAXVALUE 9223372036854775807
    NOCACHE;

CREATE SEQUENCE seq_tender_ref_no
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    NOCACHE;

CREATE TABLE tender
(
    id               NUMBER(19)          NOT NULL,
    description      VARCHAR2(2000 CHAR) NOT NULL,
    reference_number VARCHAR2(50 CHAR)   NOT NULL,
    creation_date    DATE                NOT NULL,
    deadline         DATE                NOT NULL
);

CREATE UNIQUE INDEX index_name ON tender(reference_number);