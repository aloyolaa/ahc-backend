package com.petrotal.ahcbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DataSignatoryDto(
        @NotNull(message = "{NotNull.dataSignatory.user}")
        Long user
) {
}
