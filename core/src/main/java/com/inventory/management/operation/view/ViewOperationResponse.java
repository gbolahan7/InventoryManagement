package com.inventory.management.operation.view;

import com.inventory.management.operation.OperationResponse;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.Optional;

@AllArgsConstructor
@EqualsAndHashCode
public class ViewOperationResponse<T> implements OperationResponse {
    private T entity;

    public Optional<T> getEntity() {
        return Optional.ofNullable(entity);
    }
}
