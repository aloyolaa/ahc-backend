package com.petrotal.ahcbackend.dto;

public record SignatoryDto(
        Long id,
        String sealFile,
        String signatureFile
) {
}
