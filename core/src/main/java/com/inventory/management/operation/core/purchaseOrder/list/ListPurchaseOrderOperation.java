package com.inventory.management.operation.core.purchaseOrder.list;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.PurchaseOrder;
import com.inventory.management.operation.core.purchaseOrder.list.query.PurchaseOrderQueryMapper;
import com.inventory.management.operation.list.ListOperation;
import com.inventory.management.repository.PurchaseOrderRepository;
import com.inventory.management.util.PageRequestHelper;
import com.querydsl.core.types.Predicate;

import java.util.Map;

@Operation
public class ListPurchaseOrderOperation extends ListOperation<PurchaseOrder, Long, PurchaseOrderRepository> {

    public ListPurchaseOrderOperation(PurchaseOrderRepository repository, PageRequestHelper pageRequestHelper) {
        super(repository, pageRequestHelper, PurchaseOrder.class, "id");
    }

    @Override
    public Predicate getPredicate(Map<String, Object> filter) {
        return PurchaseOrderQueryMapper.toPredicate(filter);
    }
}
