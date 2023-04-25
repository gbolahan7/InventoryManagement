package com.inventory.management.operation.core.purchaseOrder.list;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.PurchaseOrderRequest;
import com.inventory.management.operation.core.purchaseOrder.list.query.PurchaseOrderRequestQueryMapper;
import com.inventory.management.operation.list.ListOperation;
import com.inventory.management.repository.PurchaseOrderRequestRepository;
import com.inventory.management.util.PageRequestHelper;
import com.querydsl.core.types.Predicate;

import java.util.Map;

@Operation
public class ListPurchaseOrderRequestOperation extends ListOperation<PurchaseOrderRequest, Long, PurchaseOrderRequestRepository> {

    public ListPurchaseOrderRequestOperation(PurchaseOrderRequestRepository repository, PageRequestHelper pageRequestMapper) {
        super(repository, pageRequestMapper, PurchaseOrderRequest.class, "requestId");
    }

    @Override
    protected Predicate getPredicate(Map<String, Object> filter) {
        return PurchaseOrderRequestQueryMapper.toPredicate(filter);
    }
}
