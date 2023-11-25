package com.inventory.management.vo.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductEarningLiveChart {
    private String productName;
    private List<LiveChart> liveChart;
    private double dailyIncome;
    private Delta delta;
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Delta{
        private boolean up;
        private double value;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class LiveChart {
        // List.of("2020/12/02", "323.32")
        private List<Object> value;
    }

}