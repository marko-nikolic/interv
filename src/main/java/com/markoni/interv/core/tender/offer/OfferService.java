package com.markoni.interv.core.tender.offer;

import com.markoni.interv.api.tender.command.CreateOfferCommand;
import com.markoni.interv.api.tender.command.UpdateOfferCommand;
import com.markoni.interv.api.tender.model.Offer;
import com.markoni.interv.api.tender.query.OfferQuery;
import com.markoni.interv.commons.page.PageResponse;
import com.markoni.interv.commons.page.PageableRequest;

public interface OfferService {

    Offer create(CreateOfferCommand cmd);

    Offer update(String refNumber, UpdateOfferCommand cmd);

    PageResponse<Offer> search(OfferQuery query, PageableRequest request);
}
