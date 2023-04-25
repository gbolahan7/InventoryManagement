package com.inventory.management.operation.core.purchaseOrder.view;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.PurchaseOrder;
import com.inventory.management.operation.view.ViewAuditOperation;
import com.inventory.management.util.audit.AuditService;

@Operation
public class ViewPurchaseOrderAuditOperation extends ViewAuditOperation<PurchaseOrder, Long> {
    public ViewPurchaseOrderAuditOperation(AuditService service) {
        super(service, PurchaseOrder.class, "id");
    }
}
