package com.markoni.interv.core.tender.offer;

import com.markoni.interv.api.tender.command.CreateOfferCommand;
import com.markoni.interv.api.tender.model.Offer;
import com.markoni.interv.core.tender.TenderMapper;
import com.markoni.interv.core.tender.bidder.BidderMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { TenderMapper.class, BidderMapper.class })
public interface OfferMapper {

    @Mapping(target = "submissionDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "status", constant = "SUBMITTED")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tender", ignore = true)
    @Mapping(target = "bidder", ignore = true)
    @Mapping(target = "referenceNumber", ignore = true)
    OfferEntity map(CreateOfferCommand cmd);

    Offer map(OfferEntity entity);
}
