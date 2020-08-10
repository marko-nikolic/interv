package com.markoni.interv.core.tender.issuer;

import com.markoni.interv.api.tender.Issuer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IssuerMapper {

    Issuer map(IssuerEntity entity);
}
