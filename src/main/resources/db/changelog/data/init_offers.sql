INSERT INTO offer(id, bidder_id, tender_id, reference_number, submission_date, status)
VALUES (seq_offer_id.nextval,
        (SELECT id FROM bidder WHERE identification_number = 'RE32333'),
        (SELECT id FROM tender WHERE reference_number = '0001'),
        '123',
        CURRENT_DATE,
        'SUBMITTED');

INSERT INTO offer(id, bidder_id, tender_id, reference_number, submission_date, status)
VALUES (seq_offer_id.nextval,
        (SELECT id FROM bidder WHERE identification_number = 'SB4435566'),
        (SELECT id FROM tender WHERE reference_number = '0001'),
        '456',
        CURRENT_DATE,
        'SUBMITTED');