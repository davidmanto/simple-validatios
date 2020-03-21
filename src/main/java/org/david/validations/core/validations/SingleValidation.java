package org.david.validations.core.validations;

import lombok.RequiredArgsConstructor;
import org.david.validations.core.IValidation;
import org.david.validations.core.ValidationError;
import org.david.validations.core.ValidationResult;

import java.util.Collections;
import java.util.Set;
import java.util.function.BooleanSupplier;

/**
 * A single condition validation <br/><br/>
 *
 * Accepts in constructor<br/>
 * <b>supplier</b> - {@link BooleanSupplier} that represent the validation condition,
 * should return true for the validation to pass, and false for the validation to fail.
 * <br/>
 * <b>errorCode</b> - {@link String} for an error code, that should represent a
 * logical name of the validation.
 * <br/>
 * <b>errorMessage</b> - {@link String} for an error message, That should represent
 * why the validation failed in human readable form.
 */
@RequiredArgsConstructor
public class SingleValidation implements IValidation {

    protected final BooleanSupplier supplier;
    protected final String errorCode;
    protected final String errorMessage;

    @Override
    public ValidationResult validate() {
        if (supplier.getAsBoolean()) {
            return ValidationResult.passed();
        } else {
            return ValidationResult.of(getErrors());
        }
    }

    public Set<ValidationError> getErrors() {
        return Collections.singleton(new ValidationError(errorCode, errorMessage));
    }

}
