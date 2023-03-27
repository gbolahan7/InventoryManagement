package com.inventory.management.operation.core.product.list.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAuditQuery {
    private String revisionType;
    private String modifiedDateFrom;
    private String modifiedDateTo;
    private String modifiedBy;
}
