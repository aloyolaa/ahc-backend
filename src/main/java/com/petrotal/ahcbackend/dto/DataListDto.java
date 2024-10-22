package com.petrotal.ahcbackend.dto;

import java.time.LocalDate;

public record DataListDto(
        Long id,
        String voucherNumber,
        LocalDate dispatchDate
) {
}
