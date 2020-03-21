package org.david.validations.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.david.validations.core.validations.FormattedMessageValidation;
import org.david.validations.core.validations.SingleValidation;
import org.david.validations.core.validations.ValidationSequence;

import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Validations {

    public static final String QUICK_VALIDATION_CODE = "QUICK_VALIDATION";
    public static final String QUICK_VALIDATION_MESSAGE = "Quick Validation Failed";

    /**
     * Performs a {@link ValidationSequence} validation on given List of {@link IValidation},
     * with fail fast {@link ValidationSequence.Strategy} (Will stop after first failed validation)
     *
     * @return ValidationResult of first failed validation if failed, or a passed result.
     */
    public static ValidationResult failFast(List<IValidation> iValidationList) {
        IValidation validation = new ValidationSequence(iValidationList, ValidationSequence.Strategy.FAIL_FAST);
        return validation.validate();
    }

    /**
     * @see Validations#failFast(List)
     */
    public static ValidationResult failFast(IValidation... iValidations) {
        return failFast(Arrays.asList(iValidations));
    }

    /**
     * Performs a {@link ValidationSequence} validation on a list {@link IValidation},
     * with all {@link ValidationSequence.Strategy} (Will stop after first failed validation)
     *
     * @return ValidationResult of all failed validations, or a passed result.
     */
    public static ValidationResult validateAll(List<IValidation> iValidationList) {
        IValidation validation = new ValidationSequence(iValidationList, ValidationSequence.Strategy.ALL);
        return validation.validate();
    }

    /**
     * @see Validations#validateAll(List)
     */
    public static ValidationResult validateAll(IValidation... iValidations) {
        return validateAll(Arrays.asList(iValidations));
    }

    /**
     * Creates a new single validation, without yet validating.
     *
     * @return new {@link SingleValidation}
     * @see SingleValidation The behaviour of this validation
     */
    public static IValidation single(BooleanSupplier booleanSupplier, String errorCode, String errorMessage) {
        return new SingleValidation(booleanSupplier, errorCode, errorMessage);
    }

    /**
     * Creates a new single validation with formatted message, without yet validating.
     *
     * @return new {@link FormattedMessageValidation}
     * @see FormattedMessageValidation The behaviour of this validation
     */
    public static IValidation formatted(BooleanSupplier booleanSupplier, String errorCode, String errorMessage, String... args) {
        return new FormattedMessageValidation(booleanSupplier, errorCode, errorMessage, args);
    }

    /**
     * Creates a new sequence of one or more {@link IValidation} with desired validation strategy, without yet validating.
     *
     * @return new {@link ValidationSequence}
     * @see ValidationSequence The behavior of validation sequence
     */
    public static IValidation sequence(ValidationSequence.Strategy strategy, List<IValidation> iValidationList) {
        return new ValidationSequence(iValidationList, strategy);
    }

    /**
     * @see Validations#sequence(ValidationSequence.Strategy, List)
     */
    public static IValidation sequence(ValidationSequence.Strategy strategy, IValidation... validations) {
        return sequence(strategy, Arrays.asList(validations));
    }

    /**
     * Creates a new sequence of one or more {@link IValidation} with the {@link ValidationSequence#DEFAULT_STRATEGY},
     * without yet validating.
     *
     * @return new {@link ValidationSequence}
     * @see ValidationSequence The behavior of validation sequence
     */
    public static IValidation sequence(List<IValidation> iValidationList) {
        return new ValidationSequence(iValidationList);
    }

    /**
     * @see Validations#sequence(List)
     */
    public static IValidation sequence(IValidation... iValidations) {
        return new ValidationSequence(Arrays.asList(iValidations));
    }

    /**
     * Creates a new {@link SingleValidation} and performs validation.
     * If validation has failed, will return a {@link ValidationResult} with default error code and message.
     *
     * @param booleanSupplier A condition for validation to pass.
     * @return ValidationResult, If validation has failed, error code will be {@link Validations#QUICK_VALIDATION_CODE},
     * and error message will be {@link Validations#QUICK_VALIDATION_MESSAGE}
     */
    public static ValidationResult quickValidate(BooleanSupplier booleanSupplier) {
        IValidation validation = new SingleValidation(booleanSupplier, QUICK_VALIDATION_CODE, QUICK_VALIDATION_MESSAGE);
        return validation.validate();
    }

}
