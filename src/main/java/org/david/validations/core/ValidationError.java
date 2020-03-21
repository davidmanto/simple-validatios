package org.david.validations.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * An object represent a validation error, with a error code representing a logical value, and an error message in
 * human readable language.
 */
@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ValidationError {

    @EqualsAndHashCode.Include
    private final String code;

    private final String message;
}
