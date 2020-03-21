package org.david.validations.core;

import java.util.Set;
import java.util.stream.Collectors;

public class ValidationCoreUtils {

    public static String formatExceptionMessage(Set<ValidationError> validationErrors) {
        return validationErrors.stream()
                .map(err -> String.format("\tValidation Failed [%s] : \"%s\"", err.getCode(), err.getMessage()))
                .collect(Collectors.joining("\n", "\n", "\n"));
    }
}
