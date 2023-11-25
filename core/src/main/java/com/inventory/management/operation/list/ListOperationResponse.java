package com.inventory.management.operation.list;

import com.inventory.management.operation.OperationResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@AllArgsConstructor
@Getter
public class ListOperationResponse<T> implements OperationResponse {
    private final Page<T> page;
}
