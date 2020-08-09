package com.markoni.interv.core.tender;

import com.markoni.interv.api.tender.CreateTenderCommand;
import com.markoni.interv.api.tender.Tender;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class TenderMapper {

    @Mapping(target = "creationDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "referenceNumber", ignore = true)
    public abstract TenderEntity map(CreateTenderCommand cmd);

    public abstract Tender map(TenderEntity tender);
}
