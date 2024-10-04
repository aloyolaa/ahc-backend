package com.petrotal.ahcbackend.dto;

public record UserSignatoryDto(
        Long id,
        String firstName,
        String lastName,
        String role
) {
}
