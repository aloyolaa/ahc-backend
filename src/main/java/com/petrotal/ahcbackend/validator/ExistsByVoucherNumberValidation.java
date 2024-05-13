package com.petrotal.ahcbackend.validator;

import com.petrotal.ahcbackend.service.DataAccessService;
import com.petrotal.ahcbackend.validator.annotation.ExistsByVoucherNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistsByVoucherNumberValidation implements ConstraintValidator<ExistsByVoucherNumber, String> {
    private final DataAccessService dataAccessService;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !dataAccessService.existsByVoucherNumber(s);
    }
}
