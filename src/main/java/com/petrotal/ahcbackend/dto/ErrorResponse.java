package com.petrotal.ahcbackend.dto;

public record ErrorResponse(
        String title,
        Object message
) {
}
