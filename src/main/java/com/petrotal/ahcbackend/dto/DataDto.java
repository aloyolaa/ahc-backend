package com.petrotal.ahcbackend.dto;

import com.petrotal.ahcbackend.validator.annotation.ExistsByVoucherNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record DataDto(
        @NotBlank(message = "{NotBlank.data.voucherNumber}")
        @ExistsByVoucherNumber
        String voucherNumber,
        @NotNull(message = "{NotNull.data.dispatchDate}")
        @PastOrPresent(message = "{PastOrPresent.data.dispatchDate}")
        LocalDate dispatchDate,
        @NotBlank(message = "{NotBlank.data.description}")
        String description,
        @NotNull(message = "{NotNull.data.consumption}")
        @Positive(message = "{Positive.data.consumption}")
        Double consumption,
        @NotNull(message = "{NotNull.data.area}")
        Long area,
        @NotNull(message = "{NotNull.data.contractor}")
        Long contractor,
        @NotNull(message = "{NotNull.data.equipment}")
        Long equipment
) {
}
