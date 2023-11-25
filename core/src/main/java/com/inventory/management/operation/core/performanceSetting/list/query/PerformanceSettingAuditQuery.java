package com.inventory.management.operation.core.performanceSetting.list.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerformanceSettingAuditQuery {
    private String revisionType;
    private String modifiedDateFrom;
    private String modifiedDateTo;
    private String modifiedBy;
}
