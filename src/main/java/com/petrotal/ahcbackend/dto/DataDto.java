package com.petrotal.ahcbackend.dto;

import com.petrotal.ahcbackend.validator.annotation.ExistsByVoucherNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record DataDto(
        @NotBlank(message = "{NotBlank.gas.voucherNumber}")
        @ExistsByVoucherNumber
        String voucherNumber,
        @NotNull(message = "{NotNull.gas.dispatchDate}")
        @PastOrPresent(message = "{PastOrPresent.gas.dispatchDate}")
        LocalDate dispatchDate,
        @NotBlank(message = "{NotBlank.gas.description}")
        String description,
        @NotNull(message = "{NotNull.gas.consumption}")
        @Positive(message = "{Positive.gas.consumption}")
        Double consumption,
        @NotNull(message = "{NotNull.gas.area}")
        Long area,
        @NotNull(message = "{NotNull.gas.contractor}")
        Long contractor,
        @NotNull(message = "{NotNull.gas.equipment}")
        Long equipment
) {
}
