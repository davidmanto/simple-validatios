package usage;

import org.david.validations.core.IValidation;
import org.david.validations.core.ValidationResult;
import org.david.validations.core.Validations;
import org.david.validations.core.exceptions.ValidationException;

public class Formatted {

    public static void main(String[] args) {
        int num = 10;
        String errorCode = "NUMBER_TOO_SMALL";
        String format = "Number should be bigger than %s, was %s!";

        IValidation validation =
                Validations.formatted(() -> num > 20, errorCode, format, String.valueOf(20), String.valueOf(num));

        ValidationResult validationResult = validation.validate();

        try {
            validationResult.throwIfFailed();
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }
}
