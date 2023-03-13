package com.inventory.management.operation.core.category.list.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryAuditQuery {
    private String revisionType;
    private String modifiedDateFrom;
    private String modifiedDateTo;
    private String modifiedBy;
}
