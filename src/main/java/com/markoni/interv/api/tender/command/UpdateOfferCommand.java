package com.markoni.interv.api.tender.command;

import com.markoni.interv.api.tender.model.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOfferCommand {

    private OfferStatus status;
}
