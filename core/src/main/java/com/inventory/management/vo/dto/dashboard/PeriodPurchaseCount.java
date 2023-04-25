package com.inventory.management.vo.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class PeriodPurchaseCount {
    private Map<String, PeriodLevel> levelMap;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class PeriodLevel{
        private long prev;
        private long current;
        private long next;
    }
}
