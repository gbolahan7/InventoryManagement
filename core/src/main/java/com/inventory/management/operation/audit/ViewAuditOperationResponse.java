package com.inventory.management.operation.audit;

import com.inventory.management.operation.OperationResponse;
import com.inventory.management.util.audit.DefaultAudit;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.Optional;

@AllArgsConstructor
@EqualsAndHashCode
public class ViewAuditOperationResponse<T> implements OperationResponse {
    private DefaultAudit<T> auditedEntity;

    public Optional<DefaultAudit<T>> getAuditedEntity() {
        return Optional.ofNullable(auditedEntity);
    }
}
