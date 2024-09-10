package com.petrotal.ahcbackend.validator;

import com.petrotal.ahcbackend.service.data.ContractorService;
import com.petrotal.ahcbackend.validator.annotation.ExistsByContractorName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistsByContractorNameValidation implements ConstraintValidator<ExistsByContractorName, String> {
    private final ContractorService contractorService;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !contractorService.existsByName(s.toUpperCase());
    }
}
