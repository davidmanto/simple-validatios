package org.david.validations.core.validations;

import org.david.validations.core.IValidation;
import org.david.validations.core.ValidationMocker;
import org.david.validations.core.ValidationResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FormattedMessageValidationTest {

    private static final String DEFAULT_ERROR_CODE = "CODE";
    private static final String DEFAULT_ERROR_FORMAT = "%s:%s";
    private static final String PARAM1 = "A";
    private static final String PARAM2 = "B";
    private static final String FORMATTED_ERROR = String.format(DEFAULT_ERROR_FORMAT, PARAM1, PARAM2);

    @Test
    void when_containsError_verifyMessageFormatting() {
        IValidation formattedMessageValidation =
                new FormattedMessageValidation(ValidationMocker.failedCondition(), DEFAULT_ERROR_CODE, DEFAULT_ERROR_FORMAT, PARAM1, PARAM2);

        ValidationResult validationResult = formattedMessageValidation.validate();

        assertTrue(validationResult.getSingleError().isPresent());
        assertEquals(validationResult.getSingleError().get().getMessage(), FORMATTED_ERROR);

    }
}