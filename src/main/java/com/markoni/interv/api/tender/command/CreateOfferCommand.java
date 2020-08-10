package com.markoni.interv.api.tender.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOfferCommand {

    @NotBlank(message = "Tender reference number is mandatory")
    private String tenderReferenceNumber;

    @NotBlank(message = "Bidder identification number is mandatory")
    private String bidderIdNumber;

    private String note;
}
