package com.petrotal.ahcbackend.dto;

import java.time.LocalDate;
import java.util.List;

public record DataViewDto(
        Long id,
        String voucherNumber,
        LocalDate dispatchDate,
        String usageDetail,
        String materialStatus,
        String observations,
        String area,
        String contractor,
        String equipment,
        String status,
        List<DataDetailDto> details,
        List<UserSignatoryDto> signatories
) {
}
