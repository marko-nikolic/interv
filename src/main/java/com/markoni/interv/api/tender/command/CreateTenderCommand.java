package com.markoni.interv.api.tender.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTenderCommand {

    @NotBlank(message = "Issuer identification number is mandatory")
    private String issuerIdNumber;

    @NotBlank(message = "Description must be provided")
    private String description;

    @NotNull(message = "Deadline date must be provided")
    private LocalDate deadline;

}
