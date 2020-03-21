package org.david.validations.core;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ValidationResultTest {

    private final static String DEFAULT_ERROR_1_MESSAGE = "Message1";
    private final static String DEFAULT_ERROR_1_CODE = "Code1";

    private final static String DEFAULT_ERROR_2_MESSAGE = "Message2";
    private final static String DEFAULT_ERROR_2_CODE = "Code2";

    private final static String DEFAULT_ERROR_3_MESSAGE = "Message3";
    private final static String DEFAULT_ERROR_3_CODE = "Code3";

    @Test
    void when_of_withEmptySet_getPassedResult() {
        ValidationResult validationResult = ValidationResult.of(Collections.emptySet());

        assertTrue(validationResult.getValidationErrors().isEmpty());
        assertTrue(validationResult.isPassed());
    }

    @Test
    void when_of_withNullSet_getPassedResult() {
        ValidationResult validationResult = ValidationResult.of(null);

        assertTrue(validationResult.getValidationErrors().isEmpty());
        assertTrue(validationResult.isPassed());
    }

    @Test
    void when_of_withPopulatedSet_getFailedResult() {
        ValidationResult validationResult = ValidationResult.of(mockSetWithOneError());

        assertEquals(validationResult.getValidationErrors().size(), 1);
        assertFalse(validationResult.isPassed());
    }


    @Test
    void when_ofSingleError_containsFailedResult() {
        ValidationResult validationResult = ValidationResult.ofSingleError(DEFAULT_ERROR_1_CODE, DEFAULT_ERROR_1_MESSAGE);

        assertEquals(validationResult.getValidationErrors().size(), 1);
        assertFalse(validationResult.isPassed());
    }

    @Test
    void when_hasError_doesContain() {
        ValidationResult validationResult = ValidationResult.of(mockSetWithOneError());

        assertTrue(validationResult.hasError(DEFAULT_ERROR_1_CODE));
    }

    @Test
    void when_hasError_doesNotContain() {
        ValidationResult validationResult = ValidationResult.of(mockSetWithOneError());

        assertFalse(validationResult.hasError(DEFAULT_ERROR_2_CODE));
    }

    @Test
    void when_hasAllErrors_doesContainAll() {
        ValidationResult validationResult = ValidationResult.of(mockSetWithThreeErrors());

        assertTrue(validationResult.hasAllErrors(DEFAULT_ERROR_1_CODE, DEFAULT_ERROR_2_CODE, DEFAULT_ERROR_3_CODE));
    }

    @Test
    void when_hasAllErrors_doesNotContainAll() {
        ValidationResult validationResult = ValidationResult.of(mockSetWithTwoErrors());

        assertFalse(validationResult.hasAllErrors(DEFAULT_ERROR_1_CODE, DEFAULT_ERROR_2_CODE, DEFAULT_ERROR_3_CODE));
    }


    @Test
    void when_hasAnyError_doesContainAny() {
        ValidationResult validationResult = ValidationResult.of(mockSetWithThreeErrors());

        assertTrue(validationResult.hasAnyError(DEFAULT_ERROR_1_CODE, DEFAULT_ERROR_2_CODE));
    }

    @Test
    void when_hasAnyError_doesNotContainAny() {
        ValidationResult validationResult = ValidationResult.of(mockSetWithOneError());

        assertFalse(validationResult.hasAnyError(DEFAULT_ERROR_2_CODE, DEFAULT_ERROR_3_CODE));
    }

    @Test
    void when_getSingleError_hasError_optionalHasIt() {
        ValidationResult validationResult = ValidationResult.of(mockSetWithOneError());

        assertTrue(validationResult.getSingleError().isPresent());
        assertEquals(validationResult.getSingleError().get().getCode(), DEFAULT_ERROR_1_CODE);
    }

    @Test
    void when_getSingleError_noError_optionalIsEmpty(){
        ValidationResult validationResult = ValidationResult.of(Collections.emptySet());

        assertTrue(validationResult.getSingleError().isEmpty());
    }

    private Set<ValidationError> mockSetWithOneError() {
        return Set.of(
                new ValidationError(DEFAULT_ERROR_1_CODE, DEFAULT_ERROR_1_MESSAGE));
    }

    private Set<ValidationError> mockSetWithTwoErrors() {
        return Set.of(
                new ValidationError(DEFAULT_ERROR_1_CODE, DEFAULT_ERROR_1_MESSAGE),
                new ValidationError(DEFAULT_ERROR_2_CODE, DEFAULT_ERROR_2_MESSAGE));
    }

    private Set<ValidationError> mockSetWithThreeErrors() {
        return Set.of(
                new ValidationError(DEFAULT_ERROR_1_CODE, DEFAULT_ERROR_1_MESSAGE),
                new ValidationError(DEFAULT_ERROR_2_CODE, DEFAULT_ERROR_2_MESSAGE),
                new ValidationError(DEFAULT_ERROR_3_CODE, DEFAULT_ERROR_3_MESSAGE));
    }
}