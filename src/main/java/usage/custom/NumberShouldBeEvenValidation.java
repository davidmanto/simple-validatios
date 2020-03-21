package usage.custom;

import lombok.RequiredArgsConstructor;
import org.david.validations.core.IValidation;
import org.david.validations.core.ValidationResult;

@RequiredArgsConstructor
public class NumberShouldBeEvenValidation implements IValidation {

    private static final String errorCode = "UNEVEN_NUMBER";
    private static final String errorMessage = "Number should be even!";

    private final int theNumber;

    @Override
    public ValidationResult validate() {
        if (theNumber % 2 == 0) {
            return ValidationResult.passed();
        } else {
            return ValidationResult.ofSingleError(errorCode, errorMessage);
        }
    }
}
