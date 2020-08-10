package com.markoni.interv.core.tender.bidder;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@Table(name = "bidder")
public class BidderEntity {

    @Id
    @SequenceGenerator(name = "bidderIdGenerator", sequenceName = "seq_bidder_id", allocationSize = 1)
    @GeneratedValue(generator = "bidderIdGenerator", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "identification_number", nullable = false)
    private String identificationNumber;

    @Column(name = "name", nullable = false)
    private String name;

}
