package usage;

import org.david.validations.core.IValidation;
import org.david.validations.core.ValidationResult;
import org.david.validations.core.Validations;
import org.david.validations.core.exceptions.ValidationException;
import org.david.validations.core.validations.ValidationSequence;

public class NestedValidations {

    public static void main(String[] args) {
        int number = 9;

        IValidation validation = Validations.sequence(
                ValidationSequence.Strategy.ALL,
                Validations.single(() -> number > 0, "CODE1", "A validation that will pass"),
                Validations.single(() -> number > 15, "CODE2", "A validation that will fail"),
                Validations.single(() -> number > 20, "CODE3", "Will reach here too!")
        );

        try {
            validation.validate().throwIfFailed();
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        // Alternative usage

        ValidationResult validationResult = Validations.validateAll(
                Validations.single(() -> number > 0, "CODE1", "A validation that will pass"),
                Validations.single(() -> number > 15, "CODE2", "A validation that will fail"),
                Validations.single(() -> number > 20, "CODE3", "Will reach here too!")
        );

        try {
            validationResult.throwIfFailed();
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }
}
