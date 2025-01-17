package com.petrotal.ahcbackend.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record DataDto(
        Long id,
        @NotBlank(message = "{NotBlank.data.voucherNumber}")
        //@ExistsByVoucherNumber
        String voucherNumber,
        @NotNull(message = "{NotNull.data.dispatchDate}")
        @PastOrPresent(message = "{PastOrPresent.data.dispatchDate}")
        LocalDate dispatchDate,
        @NotBlank(message = "{NotBlank.data.usageDetail}")
        String usageDetail,
        @NotBlank(message = "{NotBlank.data.materialStatus}")
        String materialStatus,
        String observations,
        @NotNull(message = "{NotNull.data.area}")
        Long area,
        @NotNull(message = "{NotNull.data.contractor}")
        Long contractor,
        @NotNull(message = "{NotNull.data.equipment}")
        Long equipment,
        String status,
        @NotNull(message = "{NotNull.data.details}")
        @Size(min = 1, message = "{Size.data.details}")
        List<DataDetailDto> details,
        List<UserSignatoryDto> signatories
) {
}
