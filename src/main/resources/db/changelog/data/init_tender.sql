INSERT INTO tender(id, issuer_id, description, reference_number, creation_date, deadline, status)
VALUES (seq_tender_id.nextval, (SELECT id FROM issuer WHERE identification_number = 'CIN112233'),
        'Construction of houses', '0001', CURRENT_DATE, CURRENT_DATE, 'OPENED');