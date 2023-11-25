package com.inventory.management.operation.view;

import com.inventory.management.operation.OperationRequest;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class ViewOperationRequest<I> implements OperationRequest {
    private final I identifier;
}
