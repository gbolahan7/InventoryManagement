package com.inventory.management.operation.access;

import com.inventory.management.operation.OperationRequest;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class AccessOperationRequest<I> implements OperationRequest {
    private final I identifier;
}
