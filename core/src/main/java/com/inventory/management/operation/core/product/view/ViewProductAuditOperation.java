package com.inventory.management.operation.core.product.view;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.Product;
import com.inventory.management.operation.view.ViewAuditOperation;
import com.inventory.management.util.audit.AuditService;

@Operation
public class ViewProductAuditOperation extends ViewAuditOperation<Product, Long> {
    public ViewProductAuditOperation(AuditService service) {
        super(service, Product.class, "id");
    }
}
