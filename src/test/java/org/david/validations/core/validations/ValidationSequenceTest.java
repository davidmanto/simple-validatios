package org.david.validations.core.validations;

import org.david.validations.core.IValidation;
import org.david.validations.core.ValidationMocker;
import org.david.validations.core.ValidationResult;
import org.david.validations.core.Validations;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ValidationSequenceTest {
    private final static String DEFAULT_ERROR_1_MESSAGE = "Message1";
    private final static String DEFAULT_ERROR_1_CODE = "Code1";

    private final static String DEFAULT_ERROR_2_MESSAGE = "Message2";
    private final static String DEFAULT_ERROR_2_CODE = "Code2";

    private final static String DEFAULT_ERROR_3_MESSAGE = "Message3";
    private final static String DEFAULT_ERROR_3_CODE = "Code3";

    private final static String DEFAULT_ERROR_4_MESSAGE = "Message4";
    private final static String DEFAULT_ERROR_4_CODE = "Code4";

    private final static String DEFAULT_ERROR_5_MESSAGE = "Message5";
    private final static String DEFAULT_ERROR_5_CODE = "Code5";

    @Test
    void when_strategyIsFailFast_hasAnyFailedValidation_containOneError() {
        IValidation validation = Validations.sequence(ValidationSequence.Strategy.FAIL_FAST,
                ValidationMocker.valid(DEFAULT_ERROR_1_CODE, DEFAULT_ERROR_1_MESSAGE),
                ValidationMocker.invalid(DEFAULT_ERROR_2_CODE, DEFAULT_ERROR_2_MESSAGE),
                ValidationMocker.invalid(DEFAULT_ERROR_3_CODE, DEFAULT_ERROR_3_MESSAGE)
        );

        ValidationResult validationResult = validation.validate();

        assertFalse(validationResult.isPassed());
        assertEquals(validationResult.getValidationErrors().size(), 1);
        assertTrue(validationResult.hasError(DEFAULT_ERROR_2_CODE));
    }

    @Test
    void when_strategyIsFailFast_hasNoFailedValidations_containNoErrors() {
        IValidation validation = Validations.sequence(ValidationSequence.Strategy.FAIL_FAST,
                ValidationMocker.valid(DEFAULT_ERROR_1_CODE, DEFAULT_ERROR_1_MESSAGE),
                ValidationMocker.valid(DEFAULT_ERROR_2_CODE, DEFAULT_ERROR_2_MESSAGE)
        );

        ValidationResult validationResult = validation.validate();

        assertTrue(validationResult.isPassed());
        assertTrue(validationResult.getValidationErrors().isEmpty());
    }

    @Test
    void when_strategyIsAll_hasMultipleFailedValidations_containTheFailedOnes() {
        IValidation validation = Validations.sequence(ValidationSequence.Strategy.ALL,
                ValidationMocker.valid(DEFAULT_ERROR_1_CODE, DEFAULT_ERROR_1_MESSAGE),
                ValidationMocker.invalid(DEFAULT_ERROR_2_CODE, DEFAULT_ERROR_2_MESSAGE),
                ValidationMocker.invalid(DEFAULT_ERROR_3_CODE, DEFAULT_ERROR_3_MESSAGE)
        );

        ValidationResult validationResult = validation.validate();

        assertFalse(validationResult.isPassed());
        assertEquals(validationResult.getValidationErrors().size(), 2);
        assertTrue(validationResult.hasAllErrors(DEFAULT_ERROR_2_CODE, DEFAULT_ERROR_3_CODE));
    }

    @Test
    void when_strategyIsAll_allValidationsPassed_containNoErrors() {
        IValidation validation = Validations.sequence(ValidationSequence.Strategy.ALL,
                ValidationMocker.valid(DEFAULT_ERROR_1_CODE, DEFAULT_ERROR_1_MESSAGE),
                ValidationMocker.valid(DEFAULT_ERROR_2_CODE, DEFAULT_ERROR_2_MESSAGE),
                ValidationMocker.valid(DEFAULT_ERROR_3_CODE, DEFAULT_ERROR_3_MESSAGE)
        );

        ValidationResult validationResult = validation.validate();
        assertTrue(validationResult.isPassed());
        assertTrue(validationResult.getValidationErrors().isEmpty());
    }

    @Test
    void verifyInheritedBehaviour_withFailFastStrategy() {
        IValidation validation = Validations.sequence(ValidationSequence.Strategy.FAIL_FAST,
                ValidationMocker.valid(DEFAULT_ERROR_1_CODE, DEFAULT_ERROR_1_MESSAGE),
                Validations.sequence(
                        ValidationMocker.valid(DEFAULT_ERROR_2_CODE, DEFAULT_ERROR_2_MESSAGE),
                        ValidationMocker.invalid(DEFAULT_ERROR_3_CODE, DEFAULT_ERROR_3_MESSAGE),
                        ValidationMocker.valid(DEFAULT_ERROR_4_CODE, DEFAULT_ERROR_4_MESSAGE)),
                ValidationMocker.invalid(DEFAULT_ERROR_5_CODE, DEFAULT_ERROR_5_MESSAGE));

        ValidationResult validationResult = validation.validate();
        assertFalse(validationResult.isPassed());
        assertEquals(validationResult.getValidationErrors().size(), 1);
        assertTrue(validationResult.hasError(DEFAULT_ERROR_3_CODE));
    }

    @Test
    void verifyInheritedBehaviour_withAllStrategy() {
        IValidation validation = Validations.sequence(ValidationSequence.Strategy.ALL,
                ValidationMocker.invalid(DEFAULT_ERROR_1_CODE, DEFAULT_ERROR_1_MESSAGE),
                Validations.sequence(
                        ValidationMocker.valid(DEFAULT_ERROR_2_CODE, DEFAULT_ERROR_2_MESSAGE),
                        ValidationMocker.invalid(DEFAULT_ERROR_3_CODE, DEFAULT_ERROR_3_MESSAGE),
                        ValidationMocker.valid(DEFAULT_ERROR_4_CODE, DEFAULT_ERROR_4_MESSAGE)),
                ValidationMocker.invalid(DEFAULT_ERROR_5_CODE, DEFAULT_ERROR_5_MESSAGE));

        ValidationResult validationResult = validation.validate();
        assertFalse(validationResult.isPassed());
        assertEquals(validationResult.getValidationErrors().size(), 3);
        assertTrue(validationResult.hasAllErrors(DEFAULT_ERROR_1_CODE, DEFAULT_ERROR_3_CODE, DEFAULT_ERROR_5_CODE));
    }
}