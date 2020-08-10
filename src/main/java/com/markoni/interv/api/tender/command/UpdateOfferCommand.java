package com.markoni.interv.api.tender.command;

import com.markoni.interv.api.tender.model.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOfferCommand {

    @NotNull(message = "Offer status is mandatory")
    private OfferStatus status;
}
