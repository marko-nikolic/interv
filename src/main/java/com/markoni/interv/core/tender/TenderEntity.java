package com.markoni.interv.core.tender;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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
@Table(name = "tender")
public class TenderEntity {

    @Id
    @SequenceGenerator(name = "tenderIdGenerator", sequenceName = "seq_tender_id", allocationSize = 1)
    @GeneratedValue(generator = "tenderIdGenerator", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "reference_number", nullable = false)
    private String referenceNumber;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;

}
