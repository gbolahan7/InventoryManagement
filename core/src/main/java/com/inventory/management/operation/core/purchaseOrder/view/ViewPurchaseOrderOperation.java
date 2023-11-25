package com.inventory.management.operation.core.purchaseOrder.view;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.PurchaseOrder;
import com.inventory.management.operation.view.ViewOperation;
import com.inventory.management.repository.PurchaseOrderRepository;

@Operation
public class ViewPurchaseOrderOperation extends ViewOperation<PurchaseOrder, Long, PurchaseOrderRepository> {

    public ViewPurchaseOrderOperation(PurchaseOrderRepository repository) {
        super(repository);
    }
}
