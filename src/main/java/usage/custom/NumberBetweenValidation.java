package usage.custom;

import lombok.RequiredArgsConstructor;
import org.david.validations.core.IValidation;
import org.david.validations.core.ValidationResult;

@RequiredArgsConstructor
public class NumberBetweenValidation implements IValidation {
    private static final String errorCode = "NEGATIVE_NUMBER";
    private static final String errorFormat = "Number should be between %s and %s";

    private final int theNumber;
    private final int min;
    private final int max;

    @Override
    public ValidationResult validate() {
        if (theNumber >= min && theNumber <= max) {
            return ValidationResult.passed();
        } else {
            String errorMessage = String.format(errorFormat, min, max);
            return ValidationResult.ofSingleError(errorCode, errorMessage);
        }
    }
}
