package com.petrotal.ahcbackend.dto;

import com.petrotal.ahcbackend.validator.annotation.ExistsByContractorName;
import jakarta.validation.constraints.NotBlank;

public record ContractorDto(
        Long id,
        @NotBlank(message = "{NotBlank.contractor.name}")
        @ExistsByContractorName
        String name
) {
}
