package com.inventory.management.operation.create;

import com.inventory.management.operation.OperationResponse;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class CreateOperationResponse<T> implements OperationResponse {
    private final T request;
}
