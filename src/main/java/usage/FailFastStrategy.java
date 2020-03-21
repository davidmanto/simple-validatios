package usage;

import org.david.validations.core.IValidation;
import org.david.validations.core.ValidationResult;
import org.david.validations.core.Validations;
import org.david.validations.core.exceptions.ValidationException;
import org.david.validations.core.validations.ValidationSequence;

public class FailFastStrategy {

    public static void main(String[] args) {
        int number = 10;

        IValidation validation = Validations.sequence(
                ValidationSequence.Strategy.FAIL_FAST, // This is also the default strategy!
                Validations.single(() -> number > 0, "CODE1", "A validation that will pass"),
                Validations.single(() -> number > 15, "CODE2", "A validation that will fail"),
                Validations.single(() -> number > 5, "CODE3", "Wont reach this validation")
        );

        try {
            validation.validate().throwIfFailed();
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        // Alternative usage

        ValidationResult validationResult = Validations.failFast(
                Validations.single(() -> number > 0, "CODE1", "A validation that will pass"),
                Validations.single(() -> number > 15, "CODE2", "A validation that will fail"),
                Validations.single(() -> number > 5, "CODE3", "Wont reach this validation")
        );

        try {
            validationResult.throwIfFailed();
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }
}
