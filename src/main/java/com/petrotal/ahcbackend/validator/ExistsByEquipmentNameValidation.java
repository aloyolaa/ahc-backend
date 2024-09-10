package com.petrotal.ahcbackend.validator;

import com.petrotal.ahcbackend.service.data.EquipmentService;
import com.petrotal.ahcbackend.validator.annotation.ExistsByEquipmentName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistsByEquipmentNameValidation implements ConstraintValidator<ExistsByEquipmentName, String> {
    private final EquipmentService equipmentService;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !equipmentService.existsByName(s.toUpperCase());
    }
}
