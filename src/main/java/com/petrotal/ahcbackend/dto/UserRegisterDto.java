package com.petrotal.ahcbackend.dto;

import com.petrotal.ahcbackend.validator.annotation.ExistsByEmail;
import com.petrotal.ahcbackend.validator.annotation.ExistsByUsername;

public record UserRegisterDto(
        @ExistsByUsername
        String username,
        String password,
        String firstName,
        String lastName,
        @ExistsByEmail
        String email,
        Integer role
) {
}
