package com.markoni.interv.core.tender.issuer;

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
@Table(name = "issuer")
public class IssuerEntity {

    @Id
    @SequenceGenerator(name = "issuerIdGenerator", sequenceName = "seq_issuer_id", allocationSize = 1)
    @GeneratedValue(generator = "issuerIdGenerator", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "identification_number")
    private String identificationNumber;

    @Column(name = "name", nullable = false)
    private String name;
}
