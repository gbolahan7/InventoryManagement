package com.inventory.management.vo.dto.performance;

import lombok.Value;

@Value
public class PerformanceOrder {
    Long counter;
    Double amount;
    String user;
}
