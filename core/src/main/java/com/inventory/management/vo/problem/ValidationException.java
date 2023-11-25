package com.inventory.management.vo.problem;

import java.util.List;

public class ValidationException extends RuntimeException {

    private final List<ValidatorError> validatorErrors;

    public ValidationException(List<ValidatorError> validatorErrors) {
        super("error.validation");
        this.validatorErrors = validatorErrors;
    }

    public List<ValidatorError> getValidatorErrors() {
        return validatorErrors;
    }
}
