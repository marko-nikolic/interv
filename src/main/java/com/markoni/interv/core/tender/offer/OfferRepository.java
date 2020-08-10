package com.markoni.interv.core.tender.offer;

import com.markoni.interv.api.tender.query.OfferQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<OfferEntity, Long> {

    Optional<OfferEntity> findByReferenceNumber(String referenceNumber);

    List<OfferEntity> findByTenderReferenceNumber(String referenceNumber);

    @Modifying
    @Query("UPDATE OfferEntity o SET o.status = 'REJECTED' WHERE o.tender.id = :tenderId AND o.id <> :offerId")
    void rejectOffersExcluding(@Param("tenderId") Long tenderId, @Param("offerId") Long offerId);

    @Query("SELECT o FROM OfferEntity o " +
           "WHERE (:#{#query.tenderRefNo} IS NULL OR o.tender.referenceNumber = :#{#query.tenderRefNo}) " +
           "AND (:#{#query.bidderIdNo} IS NULL OR o.bidder.identificationNumber = :#{#query.bidderIdNo})")
    Page<OfferEntity> search(@Param("query") OfferQuery query, Pageable pageable);
}
