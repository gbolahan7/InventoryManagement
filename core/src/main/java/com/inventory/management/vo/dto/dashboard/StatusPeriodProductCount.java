package com.inventory.management.vo.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class StatusPeriodProductCount {
    // month, []
    private Map<String, PeriodLevel> levelMap;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class PeriodLevel{
        private List<String> period;
        private List<Long> active;
        private List<Long> inactive;
        private List<Long> total;
    }
}
