package com.petrotal.ahcbackend.validator;

import com.petrotal.ahcbackend.service.security.UserService;
import com.petrotal.ahcbackend.validator.annotation.ExistsByUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistsByUsernameValidation implements ConstraintValidator<ExistsByUsername, String> {
    private final UserService userService;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.existsByUsername(s.toUpperCase());
    }
}
