package com.petrotal.ahcbackend.dto;

public record ResponseDto(
        Object response,
        Boolean isSuccessfully
) {
}
