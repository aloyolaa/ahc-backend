package com.petrotal.ahcbackend.dto;

import com.petrotal.ahcbackend.validator.annotation.ExistsByEmail;

public record UserUpdateDto(
        String password,
        String firstName,
        String lastName,
        //@ExistsByEmail
        String email,
        Long role
) {
}
