package com.petrotal.ahcbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DataDetailDto(
        @NotBlank(message = "{NotBlank.dataDetails.code}")
        String code,
        @NotBlank(message = "{NotBlank.dataDetails.condition}")
        Integer condition,
        @NotNull(message = "{NotNull.dataDetails.orderedQuantity}")
        @Positive(message = "{Positive.dataDetails.orderedQuantity}")
        Double orderedQuantity,
        @NotBlank(message = "{NotBlank.dataDetails.description}")
        String description,
        @NotBlank(message = "{NotBlank.dataDetails.location}")
        String location,
        @NotBlank(message = "{NotBlank.dataDetails.unitOfMeasurement}")
        String unitOfMeasurement,
        @NotNull(message = "{NotNull.dataDetails.quantityShipped}")
        @Positive(message = "{Positive.dataDetails.quantityShipped}")
        Double quantityShipped,
        @NotNull(message = "{NotNull.dataDetails.unitPrice}")
        @Positive(message = "{Positive.dataDetails.unitPrice}")
        Double unitPrice,
        @NotNull(message = "{NotNull.dataDetails.finalStock}")
        @Positive(message = "{Positive.dataDetails.finalStock}")
        Double finalStock
) {
}
