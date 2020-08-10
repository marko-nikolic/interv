package com.markoni.interv.core.tender.bidder;

import com.markoni.interv.api.tender.model.Bidder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BidderMapper {

    Bidder map(BidderEntity entity);
}
