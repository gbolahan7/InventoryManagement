package com.inventory.management.vo.problem;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ValidationBuilder {

    List<ValidatorError> errors = new ArrayList<>();

    public ValidationBuilder addError(String errorCode) {
        ValidatorError validatorError = new ValidatorError();
        validatorError.setErrorKey(errorCode);
        validatorError.setArguments(new Object[] {});
        errors.add(validatorError);
        return this;
    }

    public ValidationBuilder addError(String errorCode, Object... args) {
        ValidatorError validatorError = new ValidatorError();
        validatorError.setErrorKey(errorCode);
        validatorError.setArguments(args);
        errors.add(validatorError);
        return this;
    }

    public boolean isClean() {
        return CollectionUtils.isEmpty(errors);
    }

    public List<ValidatorError> build() {
        return errors;
    }
}