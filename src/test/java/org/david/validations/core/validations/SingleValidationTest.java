package org.david.validations.core.validations;

import org.david.validations.core.IValidation;
import org.david.validations.core.ValidationMocker;
import org.david.validations.core.ValidationResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleValidationTest {

    private static final String DEFAULT_ERROR_MESSAGE = "MESSAGE";
    private static final String DEFAULT_ERROR_CODE = "CODE";

    @Test
    void when_conditionIsTrue_containErrorMessage(){
        IValidation validation = ValidationMocker.invalid(DEFAULT_ERROR_CODE, DEFAULT_ERROR_MESSAGE);

        ValidationResult validationResult = validation.validate();

        assertFalse(validationResult.isPassed());
        assertTrue(validationResult.hasError(DEFAULT_ERROR_CODE));
    }

    @Test
    void when_conditionIsFalse_returnEmptyResult(){
        IValidation validation = ValidationMocker.valid(DEFAULT_ERROR_MESSAGE, DEFAULT_ERROR_CODE);

        ValidationResult validationResult = validation.validate();

        assertTrue(validationResult.isPassed());
        assertTrue(validationResult.getValidationErrors().isEmpty());
    }
}