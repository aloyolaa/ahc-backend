package com.petrotal.ahcbackend.validator.annotation;

import com.petrotal.ahcbackend.validator.ExistsByUsernameValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistsByUsernameValidation.class)
public @interface ExistsByUsername {
    String message() default "{ExistsByUsername.user.username}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
