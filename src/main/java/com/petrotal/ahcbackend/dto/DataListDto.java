package com.petrotal.ahcbackend.dto;

import java.time.LocalDate;

public record DataListDto(
        String voucherNumber,
        LocalDate dispatchDate
) {
}
