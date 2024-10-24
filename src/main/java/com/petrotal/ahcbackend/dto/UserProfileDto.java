package com.petrotal.ahcbackend.dto;

public record UserProfileDto(
        String username,
        String firstName,
        String lastName,
        String email,
        Integer hierarchy,
        String role
) {
}
