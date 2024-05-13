package com.petrotal.ahcbackend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record GasDto(
        @NotNull(message = "{NotNull.gas.co2}")
        @Positive(message = "{Positive.gas.co2}")
        Double co2,
        @NotNull(message = "{NotNull.gas.ch4}")
        @Positive(message = "{Positive.gas.ch4}")
        Double ch4,
        @NotNull(message = "{NotNull.gas.n2o}")
        @Positive(message = "{Positive.gas.n2o}")
        Double n2o
) {
}
