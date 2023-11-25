package com.inventory.management.vo.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class StatusPeriodOrderCount {
    private Map<String, PeriodLevel> levelMap;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class PeriodLevel{
        private List<String> period;
        private List<Long> paid;
        private List<Long> pending;
        private List<Long> total;
    }
}
