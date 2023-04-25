package com.inventory.management.operation.core.purchaseOrderItem.list;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.PurchaseOrderItem;
import com.inventory.management.operation.core.purchaseOrderItem.list.query.PurchaseOrderItemQueryMapper;
import com.inventory.management.operation.list.ListOperation;
import com.inventory.management.repository.PurchaseOrderItemRepository;
import com.inventory.management.util.PageRequestHelper;
import com.querydsl.core.types.Predicate;

import java.util.Map;

@Operation
public class ListPurchaseOrderItemOperation extends ListOperation<PurchaseOrderItem, Long, PurchaseOrderItemRepository> {

    public ListPurchaseOrderItemOperation(PurchaseOrderItemRepository repository, PageRequestHelper pageRequestHelper) {
        super(repository, pageRequestHelper, PurchaseOrderItem.class, "id");
    }

    @Override
    public Predicate getPredicate(Map<String, Object> filter) {
        return PurchaseOrderItemQueryMapper.toPredicate(filter);
    }
}
