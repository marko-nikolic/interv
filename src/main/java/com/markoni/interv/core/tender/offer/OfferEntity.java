package com.markoni.interv.core.tender.offer;

import com.markoni.interv.api.tender.model.OfferStatus;
import com.markoni.interv.core.tender.TenderEntity;
import com.markoni.interv.core.tender.bidder.BidderEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@Table(name = "offer")
public class OfferEntity {

    @Id
    @SequenceGenerator(name = "offerIdGenerator", sequenceName = "seq_offer_id", allocationSize = 1)
    @GeneratedValue(generator = "offerIdGenerator", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "reference_number", nullable = false)
    private String referenceNumber;

    @Column(name = "submission_date", nullable = false)
    private LocalDate submissionDate;

    @ManyToOne
    @JoinColumn(name = "tender_id", nullable = false)
    private TenderEntity tender;

    @ManyToOne
    @JoinColumn(name = "bidder_id", nullable = false)
    private BidderEntity bidder;

    @Column(name = "note")
    private String note;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OfferStatus status;
}
