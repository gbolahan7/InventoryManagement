package com.inventory.management.operation.core.unit.list.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UnitRequestQuery {
    private String requestType;
    private String requestStatus;
    private String createdDateFrom;
    private String createdDateTo;
    private String name;
    private String description;
}
