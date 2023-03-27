package com.inventory.management.operation.core.unit.list.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitAuditQuery {
    private String revisionType;
    private String modifiedDateFrom;
    private String modifiedDateTo;
    private String modifiedBy;
}
