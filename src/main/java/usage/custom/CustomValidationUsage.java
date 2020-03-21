package usage.custom;

import org.david.validations.core.IValidation;
import org.david.validations.core.exceptions.ValidationException;

public class CustomValidationUsage {

    public static void main(String[] args) {
        int number = 100;

        IValidation validation = new NumberComposedValidation(number);

        try {
            validation.validate().throwIfFailed();
        } catch (ValidationException e) {
            e.printStackTrace();
        }

    }

}
