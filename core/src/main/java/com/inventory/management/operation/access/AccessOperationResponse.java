package com.inventory.management.operation.access;

import com.inventory.management.domain.RequestBase;
import com.inventory.management.operation.OperationResponse;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class AccessOperationResponse<T extends RequestBase> implements OperationResponse {
    private final T entity;
}
