package com.inventory.management.operation.list;

import com.inventory.management.operation.OperationResponse;
import com.inventory.management.util.audit.DefaultAudit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@AllArgsConstructor
@Getter
public class ListAuditOperationResponse<T> implements OperationResponse {
    private final Page<DefaultAudit<T>> page;
}
