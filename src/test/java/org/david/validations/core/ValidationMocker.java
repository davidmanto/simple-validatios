package org.david.validations.core;

import org.david.validations.core.validations.SingleValidation;

import java.util.function.BooleanSupplier;

public class ValidationMocker {

    public static SingleValidation valid(String defaultCode, String message) {
        return new SingleValidation(passedCondition(), defaultCode, message);
    }

    public static SingleValidation invalid(String defaultCode, String message) {
        return new SingleValidation(failedCondition(), defaultCode, message);
    }

    public static BooleanSupplier passedCondition() {
        return () -> true;
    }

    public static BooleanSupplier failedCondition() {
        return () -> false;
    }
}
