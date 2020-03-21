package usage;

import org.david.validations.core.IValidation;
import org.david.validations.core.ValidationResult;
import org.david.validations.core.Validations;
import org.david.validations.core.exceptions.ValidationException;
import org.david.validations.core.validations.ValidationSequence;

public class AllStrategy {

    public static void main(String[] args) {
        int number = 10;

        IValidation validation = Validations.sequence(
                ValidationSequence.Strategy.ALL,
                Validations.single(() -> number > 0, "CODE1", "Some validation"),
                Validations.single(() -> number > 15, "CODE2", "Another validation"),
                Validations.sequence(
                        Validations.single(() -> number > 20, "CODE3", "Nested validation 1"),
                        Validations.sequence(
                                Validations.single(() -> number > 20, "CODE4", "Sub nested 1"),
                                Validations.single(() -> number > 20, "CODE5", "Sub nested 2")),
                        Validations.single(() -> number > 22, "CODE6", "Nested validation2"))
        );

        ValidationResult validate = validation.validate();
        try {
            validate.throwIfFailed();
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        IValidation failFastNested = Validations.sequence(
                ValidationSequence.Strategy.FAIL_FAST,
                Validations.single(() -> number > 1, "CODE1", "Will pass"),
                Validations.sequence(
                        Validations.single(() -> number > 2, "CODE2", "Will pass"),
                        Validations.single(() -> number > 20, "CODE3", "The failed one!"),
                        Validations.single(() -> number > 30, "CODE4", "Will not reach"))
        );

        try {
            failFastNested.validate().throwIfFailed();
        } catch (ValidationException e) {
            e.printStackTrace();
        }

    }
}
