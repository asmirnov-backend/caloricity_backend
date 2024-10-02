package ru.caloricity.common.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom validation annotation to ensure that exactly one of the specified fields in a class is not null.
 * If more than one field is not null, or if none of the fields are not null, the validation will fail.
 */
@Target({ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExactlyOneFieldMustBeNotNullValidator.class)
public @interface ExactlyOneFieldMustBeNotNull {
    /**
     * The names of the fields to be validated.
     */
    String[] value();

    String message() default "Only one of the specified fields must be not null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
