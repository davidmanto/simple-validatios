package org.david.validations.core;

import org.david.validations.core.exceptions.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidationsTest {

    private static final String DEFAULT_ERROR_MESSAGE = "MESSAGE";
    private static final String DEFAULT_ERROR_CODE = "CODE";

    @Test
    void when_throwIfFailed_containingErrors_throwValidationException() {
        IValidation validation = ValidationMocker.invalid(DEFAULT_ERROR_MESSAGE, DEFAULT_ERROR_CODE);

        assertThrows(ValidationException.class, () -> validation.validate().throwIfFailed());
    }

    @Test
    void when_throwIfFailed_noErrors_dontThrow() {
        IValidation validation = ValidationMocker.valid(DEFAULT_ERROR_MESSAGE, DEFAULT_ERROR_CODE);

        assertDoesNotThrow(() -> validation.validate().throwIfFailed());
    }

}