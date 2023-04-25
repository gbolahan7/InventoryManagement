package com.inventory.management.vo.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class VisitorAnalytics {
    private Long totalVisitor;
    private Long newVisitorPercent;
    private List<Long> uniqueVisitors;
    private List<PageView> pageViews;

    @Getter
    @AllArgsConstructor
    public static class PageView {
        private String label;
        private Long value;
    }
}
