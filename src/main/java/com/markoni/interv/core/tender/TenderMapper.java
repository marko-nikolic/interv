package com.markoni.interv.core.tender;

import com.markoni.interv.api.tender.command.CreateTenderCommand;
import com.markoni.interv.api.tender.model.Tender;
import com.markoni.interv.core.tender.issuer.IssuerMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = IssuerMapper.class)
public interface TenderMapper {

    @Mapping(target = "creationDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "status", expression = "java(com.markoni.interv.api.tender.model.TenderStatus.OPENED)")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "referenceNumber", ignore = true)
    @Mapping(target = "issuer", ignore = true)
    TenderEntity map(CreateTenderCommand cmd);

    Tender map(TenderEntity tender);
}
