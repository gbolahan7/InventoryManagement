package com.inventory.management.operation.core.unit.view;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.Unit;
import com.inventory.management.operation.view.ViewAuditOperation;
import com.inventory.management.util.audit.AuditService;

@Operation
public class ViewUnitAuditOperation extends ViewAuditOperation<Unit, Long> {
    public ViewUnitAuditOperation(AuditService service) {
        super(service, Unit.class, "id");
    }
}
