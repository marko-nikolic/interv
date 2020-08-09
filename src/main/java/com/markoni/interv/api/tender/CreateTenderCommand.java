package com.markoni.interv.api.tender;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTenderCommand {

    @NotBlank(message = "Description must be provided")
    private String description;

    @NotNull(message = "Deadline date must be provided")
    private LocalDate deadline;
}
