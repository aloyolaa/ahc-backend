package com.petrotal.ahcbackend.validator.annotation;

import com.petrotal.ahcbackend.validator.ExistsByVoucherNumberValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistsByVoucherNumberValidation.class)
public @interface ExistsByVoucherNumber {
    String message() default "{ExistsByVoucherNumber.data.voucherNumber}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
