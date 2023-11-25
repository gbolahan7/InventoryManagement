package com.inventory.management.operation.create;

import com.inventory.management.operation.OperationRequest;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class CreateOperationRequest<T> implements OperationRequest {
    private final T request;
}
