package org.david.validations.core.validations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.david.validations.core.IValidation;
import org.david.validations.core.ValidationError;
import org.david.validations.core.ValidationResult;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

/**
 * A sequence of {@link IValidation}, When <b>validate()</b> is called on this type of validation,
 * It will iteratively validate all inner validations, based on the {@link Strategy}. <br/>
 * If one of the validations is a sequence as well, It will recursively validate it, and the inner
 * validation will inherit the same strategy, regardless of what was set for it specifically.
 * <br/><br/>
 * <p>
 * {@link Strategy#FAIL_FAST} - With this strategy, it will iteratively validate until the first
 * validation that has failed, and will immediately stop. <br/>
 * The {@link ValidationResult} will contain a {@link ValidationError} of the first failed
 * validation.
 * <br/><br/>
 * <p>
 * {@link Strategy#ALL} - With this strategy, It will iteratively validate <b>all</b> validations,
 * regardless of their result. <br/>
 * The {@link ValidationResult} will contain {@link ValidationError}s of all failed validations.
 */
@AllArgsConstructor
@RequiredArgsConstructor
public class ValidationSequence implements IValidation {

    public static final Strategy DEFAULT_STRATEGY = Strategy.FAIL_FAST;

    public enum Strategy {
        FAIL_FAST,
        ALL
    }

    private final List<IValidation> validationList;
    @Getter
    @Setter
    private Strategy strategy = DEFAULT_STRATEGY;

    @Override
    public ValidationResult validate() {
        Set<ValidationError> validationErrorSet;
        inheritChildValidationsStrategy();

        switch (strategy) {
            case ALL:
                validationErrorSet = all();
                break;
            case FAIL_FAST:
                validationErrorSet = failFast();
                break;
            default:
                throw new RuntimeException("Invalid Validation Strategy");
        }

        if (validationErrorSet.isEmpty()) {
            return ValidationResult.passed();
        } else {
            return ValidationResult.of(validationErrorSet);
        }
    }

    private void inheritChildValidationsStrategy() {
        validationList.forEach(validation -> {
            if (validation instanceof ValidationSequence) {
                ((ValidationSequence) validation).setStrategy(this.strategy);
            }
        });
    }

    private Set<ValidationError> failFast() {
        return validationList.stream()
                .map(IValidation::validate)
                .filter(not(ValidationResult::isPassed))
                .map(ValidationResult::getValidationErrors)
                .findFirst()
                .orElseGet(LinkedHashSet::new);
    }

    private Set<ValidationError> all() {
        return validationList.stream()
                .map(IValidation::validate)
                .filter(not(ValidationResult::isPassed))
                .flatMap(validationResult -> validationResult.getValidationErrors().stream())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
