package com.inventory.management.controller;

import com.inventory.management.auth.Privilege;
import com.inventory.management.service.CategoryService;
import com.inventory.management.service.DashboardService;
import com.inventory.management.vo.dto.dashboard.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("${api.base-path:/api/v1}" + "/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @RolesAllowed({Privilege.DASHBOARD_CHART_VIEW})
    @GetMapping("/profit-bar")
    public ResponseEntity<ProfitBar> getProfitBar() {
        return ResponseEntity.ok(dashboardService.getProfitBar());
    }

    @RolesAllowed({Privilege.DASHBOARD_CHART_VIEW})
    @GetMapping("/profit-stat-card")
    public ResponseEntity<ProfitStatCard> getProfitStatCard() {
        return ResponseEntity.ok(dashboardService.getProfitStatCard());
    }

    @RolesAllowed({Privilege.DASHBOARD_CHART_VIEW})
    @GetMapping("/product-earning-chart")
    public ResponseEntity<List<ProductEarningPieChart>> getProductEarningPieChart() {
        return ResponseEntity.ok(dashboardService.getProductEarningPieChart());
    }

    @RolesAllowed({Privilege.DASHBOARD_CHART_VIEW})
    @GetMapping("/product-list")
    public ResponseEntity<List<String>> getProductList() {
        return ResponseEntity.ok(dashboardService.getProductList());
    }

    @RolesAllowed({Privilege.DASHBOARD_CHART_VIEW})
    @GetMapping("/product-live-chart")
    public ResponseEntity<Map<String, ProductEarningLiveChart>> getProductLiveChart() {
        return ResponseEntity.ok(dashboardService.getProductLiveChart());
    }

    @RolesAllowed({Privilege.DASHBOARD_CHART_VIEW})
    @GetMapping("/purchase-order-period-count")
    public ResponseEntity<List<PeriodPurchaseCount>> getPeriodicCount() {
        return ResponseEntity.ok(dashboardService.getPeriodicCount());
    }

    @RolesAllowed({Privilege.DASHBOARD_CHART_VIEW})
    @GetMapping("/order-period-status-count")
    public ResponseEntity<StatusPeriodOrderCount> getOrderStatusPeriodicCount() {
        return ResponseEntity.ok(dashboardService.getOrderStatusPeriodicCount());
    }

    @RolesAllowed({Privilege.DASHBOARD_CHART_VIEW})
    @GetMapping("/product-period-status-count")
    public ResponseEntity<StatusPeriodProductCount> getProductStatusPeriodicCount() {
        return ResponseEntity.ok(dashboardService.getProductStatusPeriodicCount());
    }
    @RolesAllowed({Privilege.DASHBOARD_CHART_VIEW})
    @GetMapping("/product-order-summary-count")
    public ResponseEntity<Map<String, List<SummaryCard>>> getProductOrderSummary() {
        return ResponseEntity.ok(dashboardService.getProductOrderSummary());
    }

    @RolesAllowed({Privilege.DASHBOARD_CHART_VIEW})
    @GetMapping("/progress-info")
    public ResponseEntity<List<ProgressInfo>> getProgressInfo() {
        return ResponseEntity.ok(dashboardService.getProgressInfo());
    }

    @RolesAllowed({Privilege.DASHBOARD_CHART_VIEW})
    @GetMapping("/visitor-analytics")
    public ResponseEntity<VisitorAnalytics> getVisitorAnalytics() {
        return ResponseEntity.ok(dashboardService.getVisitorAnalytics());
    }

}
