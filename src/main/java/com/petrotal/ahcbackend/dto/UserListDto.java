package com.petrotal.ahcbackend.dto;

public record UserListDto(
        String username,
        String firstName,
        String lastName,
        String email
) {
}
