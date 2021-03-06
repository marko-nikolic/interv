package com.markoni.interv.api.tender.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Tender {

    private String referenceNumber;

    private String description;

    private LocalDate creationDate;

    private LocalDate deadline;

    private Issuer issuer;

    private TenderStatus status;
}
