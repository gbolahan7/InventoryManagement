package com.inventory.management.operation.list;

import com.inventory.management.operation.OperationRequest;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.util.Map;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class ListOperationRequest implements OperationRequest {
    private final Serializable pageRequest;
    private final Map<String, Object> filter;
}
