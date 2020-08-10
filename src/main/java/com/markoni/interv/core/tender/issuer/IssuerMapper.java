package com.markoni.interv.core.tender.issuer;

import com.markoni.interv.api.tender.model.Issuer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IssuerMapper {

    Issuer map(IssuerEntity entity);
}
