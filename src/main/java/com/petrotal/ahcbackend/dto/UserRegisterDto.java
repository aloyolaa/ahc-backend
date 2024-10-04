package com.petrotal.ahcbackend.dto;

public record UserRegisterDto(
        String username,
        String password,
        String firstName,
        String lastName,
        String email,
        Integer hierarchy,
        Integer role
) {
}
