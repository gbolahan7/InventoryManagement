package com.inventory.management.vo.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class ProfitBar {
    private List<BigDecimal> firstLine;
    private List<BigDecimal> secondLine;
}
