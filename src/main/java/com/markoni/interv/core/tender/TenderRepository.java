package com.markoni.interv.core.tender;

import com.markoni.interv.api.tender.query.TenderQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TenderRepository extends JpaRepository<TenderEntity, Long> {

    @Query(value = "SELECT seq_tender_ref_no.nextval FROM dual", nativeQuery = true)
    Long getReferenceSequenceNumber();

    Optional<TenderEntity> findByReferenceNumber(String referenceNumber);

    @Query("SELECT t FROM TenderEntity t " +
            "WHERE (:#{#query.issuerIdNo} IS NULL OR t.issuer.identificationNumber = :#{#query.issuerIdNo})")
    Page<TenderEntity> search(@Param("query") TenderQuery query, Pageable pageable);
}
