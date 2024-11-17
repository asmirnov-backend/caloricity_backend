package ru.caloricity.common.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExactlyOneFieldMustBeNotNullTest {

    private static final Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testExactlyOneFieldNotNull_Success() {
        TestClass obj = new TestClass();
        obj.field1 = "value1";
        obj.field2 = null;
        obj.field3 = null;

        Set<ConstraintViolation<TestClass>> violations = validator.validate(obj);
        assertEquals(0, violations.size(), "Validation should pass when exactly one field is not null");
    }

    @Test
    public void testExactlyOneFieldNotNull_Failure_NoFieldsNotNull() {
        TestClass obj = new TestClass();
        obj.field1 = null;
        obj.field2 = null;
        obj.field3 = null;

        Set<ConstraintViolation<TestClass>> violations = validator.validate(obj);
        assertEquals(1, violations.size(), "Validation should fail when no fields are not null");
    }

    @Test
    public void testExactlyOneFieldNotNull_Failure_MoreThanOneFieldNotNull() {
        TestClass obj = new TestClass();
        obj.field1 = "value1";
        obj.field2 = "value2";
        obj.field3 = null;

        Set<ConstraintViolation<TestClass>> violations = validator.validate(obj);
        assertEquals(1, violations.size(), "Validation should fail when more than one field is not null");
    }

    @Test
    public void testExactlyOneFieldNotNull_AllFieldsNotNull_Failure() {
        TestClass obj = new TestClass();
        obj.field1 = "value1";
        obj.field2 = "value2";
        obj.field3 = "value3";

        Set<ConstraintViolation<TestClass>> violations = validator.validate(obj);
        assertEquals(1, violations.size(), "Validation should fail when all fields are not null");
    }

    @ExactlyOneFieldMustBeNotNull({"field1", "field2", "field3"})
    public static class TestClass {
        private String field1;
        private String field2;
        private String field3;
    }
}
