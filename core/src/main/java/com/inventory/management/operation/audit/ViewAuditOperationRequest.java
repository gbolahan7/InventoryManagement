package com.inventory.management.operation.audit;

import com.inventory.management.operation.view.ViewOperation;
import com.inventory.management.operation.view.ViewOperationRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
public class ViewAuditOperationRequest<I> extends ViewOperationRequest<I> {
    private final Integer revisionId;

    public ViewAuditOperationRequest(I identifier, Integer revisionId) {
        super(identifier);
        this.revisionId = revisionId;
    }
}
