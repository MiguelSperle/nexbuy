package com.miguelsperle.nexbuy.core.infrastructure.annotations.validations;

import com.miguelsperle.nexbuy.core.infrastructure.annotations.ValidEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
    private Enum<?>[] enumValues;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.enumValues = constraintAnnotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return Arrays.stream(this.enumValues).map(Enum::name).anyMatch(enumValue -> enumValue.equals(value));
    }
}
