package org.david.validations.core;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.david.validations.core.exceptions.ValidationException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An object representing the state of a validation result, with a {@link Set} of {@link ValidationError}, and a boolean
 * stating if validation is passed or not.
 * <br/><br/>
 * <p>
 * A <b>passed</b> validation will contain empty set of errors.<br/>
 * A <b>failed</b> validation will contain set of one or more errors.<br/>
 */
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationResult {

    @Getter
    private final Set<ValidationError> validationErrors;
    private final Boolean passed;

    /**
     * @return true if the result represents a passed validation, otherwise returns false
     */
    public Boolean isPassed() {
        return passed;
    }

    /**
     * Creates a validation result from set of errors, If set is null or empty, a passed validation result,
     * Otherwise a failed validation result
     *
     * @param validationErrors Set of validation errors
     * @return Passed or failed validation result
     */
    public static ValidationResult of(Set<ValidationError> validationErrors) {
        if (Objects.isNull(validationErrors) || validationErrors.isEmpty()) {
            return passed();
        } else {
            return new ValidationResult(validationErrors, false);
        }
    }

    /**
     * Creates a validation result for a single error, Can be useful for creating custom validations.
     * @param errorCode The error code for the contained {@link ValidationError}
     * @param errorMessage The error message for the contained {@link ValidationError}
     * @return Failed validation result with one {@link ValidationError}
     */
    public static ValidationResult ofSingleError(String errorCode, String errorMessage) {
        return of(Set.of(new ValidationError(errorCode, errorMessage)));
    }

    /**
     * Creates a passed validation result
     */
    public static ValidationResult passed() {
        return new ValidationResult(Collections.emptySet(), true);
    }

    /**
     * Returns true if the validation result's error set contains an error by comparing the error code only
     *
     * @return true if contains error, otherwise returns false
     */
    public boolean hasError(String errorCode) {
        return validationErrors.contains(wrapError(errorCode));
    }

    /**
     * Returns true if the validation result's error set contains all the errors passed to the method, compares by
     * error code only.
     *
     * @return true if contains all errors, otherwise returns false
     */
    public boolean hasAllErrors(Set<String> errorSet) {
        Set<ValidationError> wrappedErrorsCollection = errorSet.stream()
                .map(this::wrapError)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return validationErrors.containsAll(wrappedErrorsCollection);
    }

    /**
     * @see ValidationResult#hasAllErrors(Set)
     */
    public boolean hasAllErrors(String... errors) {
        return hasAllErrors(Set.of(errors));
    }

    /**
     * Returns true if the validation result's error set contains any of the errors passed to the method, compares by
     * error code only.
     *
     * @return true if contains any of the errors, otherwise returns false
     */
    public boolean hasAnyError(Set<String> errorList) {
        return errorList.stream()
                .anyMatch(this::hasError);
    }

    /**
     * @see ValidationResult#hasAnyError(Set)
     */
    public boolean hasAnyError(String... errors) {
        return hasAnyError(Set.of(errors));
    }

    /**
     * Throws a {@link ValidationException} with detailed error information if the validation result is failed
     */
    public void throwIfFailed() throws ValidationException {
        if (!isPassed()) {
            throw new ValidationException(ValidationCoreUtils.formatExceptionMessage(validationErrors));
        }
    }

    /**
     * Returns a single error in the form of an optional, useful for extracting error from single validation performing
     *
     * @return an {@link Optional} of {@link ValidationError}, Or empty if validation result is passed.
     */
    public Optional<ValidationError> getSingleError() {
        Iterator<ValidationError> iterator = validationErrors.iterator();

        if (!iterator.hasNext()) {
            return Optional.empty();
        }

        return Optional.of(iterator.next());
    }

    private ValidationError wrapError(String errorCode) {
        return new ValidationError(errorCode, null);
    }

}
