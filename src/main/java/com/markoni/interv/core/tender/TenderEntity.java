package com.markoni.interv.core.tender;

import com.markoni.interv.api.tender.model.TenderStatus;
import com.markoni.interv.core.tender.issuer.IssuerEntity;
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

    @ManyToOne
    @JoinColumn(name = "issuer_id", nullable = false)
    private IssuerEntity issuer;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TenderStatus status;

}
