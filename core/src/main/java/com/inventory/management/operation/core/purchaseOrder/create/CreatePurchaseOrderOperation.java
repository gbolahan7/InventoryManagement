package com.inventory.management.operation.core.purchaseOrder.create;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.PurchaseOrderItem;
import com.inventory.management.domain.PurchaseOrderRequest;
import com.inventory.management.operation.create.CreateOperation;
import com.inventory.management.operation.create.CreateOperationRequest;
import com.inventory.management.operation.create.CreateOperationResponse;
import com.inventory.management.repository.PurchaseOrderItemRepository;
import com.inventory.management.repository.PurchaseOrderRequestRepository;
import com.inventory.management.validation.PurchaseOrderRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

@Operation
public class CreatePurchaseOrderOperation extends CreateOperation<PurchaseOrderRequest, Long, PurchaseOrderRequestRepository> {
    @Autowired
    private PurchaseOrderItemRepository purchaseOrderItemRepository;
    public CreatePurchaseOrderOperation(PurchaseOrderRequestRepository repository, PurchaseOrderRequestValidator validator) {
        super(repository, validator);
    }

    @Override
    public CreateOperationResponse<PurchaseOrderRequest> process(CreateOperationRequest<PurchaseOrderRequest> request) {
        CreateOperationResponse<PurchaseOrderRequest> response = super.process(request);
        PurchaseOrderRequest purchaseOrderRequest  = response.getRequest();
        if(!CollectionUtils.isEmpty(purchaseOrderRequest.getItems()) ) {
            for(PurchaseOrderItem item: purchaseOrderRequest.getItems()) {
                item.setPurchaseOrderRequest(purchaseOrderRequest);
            }
            purchaseOrderItemRepository.saveAll(purchaseOrderRequest.getItems());
        }
        return new CreateOperationResponse<>(purchaseOrderRequest);
    }
}
