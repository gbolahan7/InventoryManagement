package com.inventory.management.operation.core.purchaseOrder.view;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.PurchaseOrderRequest;
import com.inventory.management.operation.view.ViewOperation;
import com.inventory.management.repository.PurchaseOrderRequestRepository;

@Operation
public class ViewPurchaseOrderRequestOperation extends ViewOperation<PurchaseOrderRequest, Long, PurchaseOrderRequestRepository> {

    public ViewPurchaseOrderRequestOperation(PurchaseOrderRequestRepository repository) {
        super(repository);
    }
}

