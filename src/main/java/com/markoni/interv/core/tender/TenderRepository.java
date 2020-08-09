package com.markoni.interv.core.tender;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TenderRepository extends JpaRepository<TenderEntity, Long> {

    @Query(value = "SELECT seq_tender_ref_no.nextval FROM dual", nativeQuery = true)
    Long getReferenceSequenceNumber();
}
