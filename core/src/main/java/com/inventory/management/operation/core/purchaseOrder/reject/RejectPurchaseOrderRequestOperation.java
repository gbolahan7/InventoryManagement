package com.inventory.management.operation.core.purchaseOrder.reject;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.PurchaseOrderRequest;
import com.inventory.management.operation.access.AccessOperation;
import com.inventory.management.repository.PurchaseOrderRequestRepository;

import static com.inventory.management.util.Constant.REJECTED;

@Operation
public class RejectPurchaseOrderRequestOperation extends AccessOperation<PurchaseOrderRequest, Long, PurchaseOrderRequestRepository> {
    public RejectPurchaseOrderRequestOperation(PurchaseOrderRequestRepository repository) {
        super(repository);
    }

    @Override
    protected PurchaseOrderRequest operate(PurchaseOrderRequest requestEntity) {
        requestEntity.setRequestStatus(REJECTED);
        return repository.save(requestEntity);
    }
}
