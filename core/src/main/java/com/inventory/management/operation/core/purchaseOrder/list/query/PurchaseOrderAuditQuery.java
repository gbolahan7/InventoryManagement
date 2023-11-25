package com.inventory.management.operation.core.purchaseOrder.list.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseOrderAuditQuery {
    private String revisionType;
    private String modifiedDateFrom;
    private String modifiedDateTo;
    private String modifiedBy;
}
