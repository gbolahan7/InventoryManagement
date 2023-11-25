package com.inventory.management.validation;

import com.inventory.management.vo.problem.ValidationException;
import com.inventory.management.vo.problem.ValidatorError;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface Validator<K, T> {
    default void validate(K k, T t) {
        getOperationValidators().getOrDefault(k, entity -> {}).accept(t);
    }
    @SuppressWarnings("unchecked")
    default void handleValidatorError(List<? extends ValidatorError> errors) {
        if (!CollectionUtils.isEmpty(errors)) throw new ValidationException((List<ValidatorError>) errors);
    }

    Map<K, Consumer<T>> getOperationValidators();
}
