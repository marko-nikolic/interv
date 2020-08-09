package com.markoni.interv.core.tender;

import com.markoni.interv.api.tender.CreateTenderCommand;
import com.markoni.interv.api.tender.Tender;

import java.util.Optional;

public interface TenderService {

    Tender create(CreateTenderCommand cmd);

    Optional<Tender> get(String referenceNumber);
}
