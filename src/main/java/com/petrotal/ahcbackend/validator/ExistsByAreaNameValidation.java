package com.petrotal.ahcbackend.validator;

import com.petrotal.ahcbackend.service.data.AreaService;
import com.petrotal.ahcbackend.validator.annotation.ExistsByAreaName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistsByAreaNameValidation implements ConstraintValidator<ExistsByAreaName, String> {
    private final AreaService areaService;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !areaService.existsByName(s.toUpperCase());
    }
}
