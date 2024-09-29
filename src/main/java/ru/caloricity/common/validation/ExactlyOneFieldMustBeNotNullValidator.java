package ru.caloricity.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

/**
 * Validator for the {@link ExactlyOneFieldMustBeNotNull} annotation, which checks that only one of the specified fields is not null.
 * If more than one field is not null, or if none of the fields are not null, the validation will fail.
 */
public class ExactlyOneFieldMustBeNotNullValidator implements ConstraintValidator<ExactlyOneFieldMustBeNotNull, Object> {
    private String[] fieldNames;

    @Override
    public void initialize(ExactlyOneFieldMustBeNotNull constraintAnnotation) {
        fieldNames = constraintAnnotation.value();
    }

    @Override
    @SneakyThrows
    public boolean isValid(Object classInstance, ConstraintValidatorContext context) {
        int notNullCount = 0;

        for (String fieldName : fieldNames) {
            Field field = classInstance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object fieldValue = field.get(classInstance);
            if (fieldValue != null) {
                notNullCount++;
                if (notNullCount > 1) return false;
            }
        }

        return notNullCount == 1;
    }
}
