package com.inventory.management.operation.core.purchaseOrderItem.list.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PurchaseOrderItemQuery {
    private Long id;
    private Long purchaseOrderId;
    private String productCode;
    private String productName;
}
