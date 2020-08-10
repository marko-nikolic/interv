CREATE SEQUENCE seq_offer_id
    INCREMENT BY 1
    MINVALUE 100000
    MAXVALUE 9223372036854775807
    NOCACHE;

CREATE TABLE offer
(
    id               NUMBER(19)         NOT NULL,
    bidder_id        NUMBER(19)         NOT NULL,
    tender_id        NUMBER(19)         NOT NULL,
    reference_number VARCHAR2(100 CHAR) NOT NULL,
    submission_date  DATE               NOT NULL,
    note             VARCHAR2(2000 CHAR),
    status           VARCHAR2(20 CHAR)  NOT NULL
);

ALTER TABLE offer
    ADD CONSTRAINT pk_offer_id PRIMARY KEY (id);

ALTER TABLE offer
    ADD CONSTRAINT fk_offer_bidder_id FOREIGN KEY (bidder_id) REFERENCES bidder (id);

ALTER TABLE offer
    ADD CONSTRAINT fk_offer_tender_id FOREIGN KEY (tender_id) REFERENCES tender (id);

CREATE UNIQUE INDEX uq_offer_ref_no ON offer (reference_number);