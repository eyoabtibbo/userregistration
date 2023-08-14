package com.userreg.backendapi.registration;

import com.userreg.backendapi.registration.services.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {


    private static final String passwordPattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[_#$%.])[A-Za-z\\d\\W]{9,}$";
    @Override
    public void initialize(ValidPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return Pattern.compile(passwordPattern).matcher(password).matches();
    }
}
