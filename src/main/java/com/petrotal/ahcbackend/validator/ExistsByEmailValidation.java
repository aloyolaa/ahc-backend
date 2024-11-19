package com.petrotal.ahcbackend.validator;

import com.petrotal.ahcbackend.service.security.UserService;
import com.petrotal.ahcbackend.validator.annotation.ExistsByEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistsByEmailValidation implements ConstraintValidator<ExistsByEmail, String> {
    private final UserService userService;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.existsByEmail(s.toUpperCase());
    }
}
