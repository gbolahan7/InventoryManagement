package com.inventory.management.operation.core.unit.list.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UnitQuery {
    private Long id;
    private String name;
    private String description;
    private String status;
}
