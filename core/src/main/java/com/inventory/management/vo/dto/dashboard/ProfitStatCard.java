package com.inventory.management.vo.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class ProfitStatCard {
    private String activePeriodDate;
    private double activeFirstPeriod;
    private String previousPeriodDate;
    private double previousFirstPeriod;
    private List<BigDecimal> line;
}
