package com.inventory.management.operation.delete;

import com.inventory.management.operation.OperationResponse;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class DeleteOperationResponse<D> implements OperationResponse {
    private final D domain;
}
