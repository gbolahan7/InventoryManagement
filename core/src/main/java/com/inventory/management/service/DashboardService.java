package com.inventory.management.service;

import com.inventory.management.vo.dto.dashboard.*;

import java.util.List;
import java.util.Map;

public interface DashboardService {

    ProfitBar getProfitBar();

    ProfitStatCard getProfitStatCard();

    List<ProductEarningPieChart> getProductEarningPieChart();

    List<String> getProductList();

    Map<String, ProductEarningLiveChart> getProductLiveChart();

    List<PeriodPurchaseCount> getPeriodicCount();

    StatusPeriodOrderCount getOrderStatusPeriodicCount();

    StatusPeriodProductCount getProductStatusPeriodicCount();

    Map<String, List<SummaryCard>> getProductOrderSummary();

    List<ProgressInfo> getProgressInfo();

    VisitorAnalytics getVisitorAnalytics();


}
