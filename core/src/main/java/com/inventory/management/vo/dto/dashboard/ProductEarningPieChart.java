package com.inventory.management.vo.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductEarningPieChart {
    private String name;
    private Double value;
}
