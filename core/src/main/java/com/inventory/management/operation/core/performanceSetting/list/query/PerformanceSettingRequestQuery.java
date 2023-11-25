package com.inventory.management.operation.core.performanceSetting.list.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PerformanceSettingRequestQuery {
    private String requestType;
    private String requestStatus;
    private String createdDateFrom;
    private String createdDateTo;
    private String name;
}
