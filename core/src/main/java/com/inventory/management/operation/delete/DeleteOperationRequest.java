package com.inventory.management.operation.delete;

import com.inventory.management.operation.OperationRequest;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class DeleteOperationRequest<D, T> implements OperationRequest {
    private final D domain;
    private final T request;
}
