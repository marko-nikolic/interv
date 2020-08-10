package com.markoni.interv.core.tender.issuer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IssuerRepository extends JpaRepository<IssuerEntity, Long> {

    Optional<IssuerEntity> findByIdentificationNumber(String identificationNumber);
}
