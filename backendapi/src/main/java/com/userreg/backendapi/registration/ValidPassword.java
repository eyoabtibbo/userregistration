package com.userreg.backendapi.registration;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message() default "Invalid password. password must be at least 9 characters containing an uppercase, a number and one of these characters _ # $ % . ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
