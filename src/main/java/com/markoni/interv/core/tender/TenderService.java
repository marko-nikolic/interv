package com.markoni.interv.core.tender;

import com.markoni.interv.api.tender.command.CreateTenderCommand;
import com.markoni.interv.api.tender.model.Tender;
import com.markoni.interv.api.tender.query.TenderQuery;
import com.markoni.interv.commons.page.PageResponse;
import com.markoni.interv.commons.page.PageableRequest;

import java.util.Optional;

public interface TenderService {

    Tender create(CreateTenderCommand cmd);

    Optional<Tender> get(String referenceNumber);

    PageResponse<Tender> search(TenderQuery query, PageableRequest pageable);
}
