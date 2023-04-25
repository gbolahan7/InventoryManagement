package com.inventory.management.operation.core.purchaseOrder.delete;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.PurchaseOrder;
import com.inventory.management.domain.PurchaseOrderRequest;
import com.inventory.management.operation.delete.DeleteOperation;
import com.inventory.management.repository.PurchaseOrderRepository;
import com.inventory.management.repository.PurchaseOrderRequestRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@Operation
public class DeletePurchaseOrderOperation extends DeleteOperation<PurchaseOrder, Long, PurchaseOrderRepository, PurchaseOrderRequest, Long, PurchaseOrderRequestRepository> {
    public DeletePurchaseOrderOperation(PurchaseOrderRepository purchaseOrderRepository, PurchaseOrderRequestRepository repository) {
        super(purchaseOrderRepository, repository);
    }
}
