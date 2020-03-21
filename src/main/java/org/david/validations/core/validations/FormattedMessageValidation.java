package org.david.validations.core.validations;

import org.david.validations.core.ValidationError;

import java.util.Collections;
import java.util.Set;
import java.util.function.BooleanSupplier;

/**
 * {@link SingleValidation} but uses {@link String#format(String, Object...)} for building the
 * error message.
 * <br/><br/>
 *
 * Instead of <b>errorMessage</b>, constructor accepts <br/>
 * <b>messageFormat</b> - {@link String } The format for the error message
 * <br/>
 * <b>formatArgs</b> - {@link String}... one or more args for the message formatting
 * @see SingleValidation
 */
public class FormattedMessageValidation extends SingleValidation {

    private final String[] formatArgs;

    public FormattedMessageValidation(BooleanSupplier supplier, String errorCode, String messageFormat, String... formatArgs) {
        super(supplier, errorCode, messageFormat);
        this.formatArgs = formatArgs;
    }

    @Override
    public Set<ValidationError> getErrors() {
        return Collections.singleton(
                new ValidationError(errorCode, String.format(errorMessage, formatArgs)));
    }
}
