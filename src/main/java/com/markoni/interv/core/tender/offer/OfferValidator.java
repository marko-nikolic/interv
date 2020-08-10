package com.markoni.interv.core.tender.offer;

import com.markoni.interv.api.tender.model.OfferStatus;
import com.markoni.interv.commons.error.ValidationException;
import com.markoni.interv.commons.message.Message;
import com.markoni.interv.commons.message.Severity;

public class OfferValidator {

    public static void validateUpdate(OfferStatus status, OfferEntity offer) {
        if (offer.getStatus() != OfferStatus.SUBMITTED) {
            String text = String.format("Only submitted offers can be accepted. This offer is %s", offer.getStatus());
            Message m = Message.builder().message(text).severity(Severity.ERROR).build();
            throw new ValidationException(m);
        }

        if (offer.getStatus() == status) {
            String text = String.format("Offer already has status %s", status);
            Message m = Message.builder().message(text).severity(Severity.ERROR).build();
            throw new ValidationException(m);
        }
    }
}
