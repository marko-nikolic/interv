package com.markoni.interv.api.tender.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Offer {

    private LocalDate submissionDate;

    private Tender tender;

    private Bidder bidder;

    private String note;

    private OfferStatus status;
}
