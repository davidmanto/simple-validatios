package usage;

import org.david.validations.core.IValidation;
import org.david.validations.core.ValidationResult;
import org.david.validations.core.Validations;
import org.david.validations.core.exceptions.ValidationException;

public class Single {

    public static void main(String[] args) {
        String str = "";
        String errorCode = "STRING_IS_EMPTY";
        String errorMessage = "Empty String!";

        IValidation validation = Validations.single(() -> !str.isEmpty(), errorCode, errorMessage);
        ValidationResult validationResult = validation.validate();

        try {
            validationResult.throwIfFailed();
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }
}
