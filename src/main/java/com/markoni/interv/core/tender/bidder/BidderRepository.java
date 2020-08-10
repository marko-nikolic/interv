package com.markoni.interv.core.tender.bidder;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BidderRepository extends JpaRepository<BidderEntity, Long> {

    Optional<BidderEntity> findByIdentificationNumber(String identificationNumber);
}
