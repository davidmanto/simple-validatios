package usage.custom;

import lombok.RequiredArgsConstructor;
import org.david.validations.core.IValidation;
import org.david.validations.core.ValidationResult;
import org.david.validations.core.Validations;

@RequiredArgsConstructor
public class NumberComposedValidation implements IValidation {
    private static final String errorCode = "INVALID_NUMBER";
    private static final String errorMessage = "Number didn't pass validations";

    private static final int MIN = 10;
    private static final int MAX = 20;


    private final int theNumber;

    @Override
    public ValidationResult validate() {
        return Validations.failFast(
                new NumberBetweenValidation(theNumber, MIN, MAX),
                new NumberShouldBeEvenValidation(theNumber)
        );
    }
}
