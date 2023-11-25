package com.inventory.management.controller;

import com.inventory.management.auth.Privilege;
import com.inventory.management.service.ReportService;
import com.inventory.management.vo.request.PageRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base-path:/api/v1}" + "/report/{format}")
public class ReportController {

    private static final Map<String, MediaType> mediaFormat = Map.of(
            "xlsx", MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
            "pdf", MediaType.APPLICATION_PDF
    );
    private final ReportService reportService;


    @RolesAllowed({Privilege.REPORT_LIST_VIEW})
    @GetMapping("/category")
    public HttpEntity<byte[]> getCategoryReport(@PathVariable String format, PageRequest pageRequest, @RequestParam(value = "", required = false) Map<String, Object> filter) {
        byte[] data = reportService.getCategories(pageRequest, format, filter);
        return new HttpEntity<>(data, getResultHeader(format, data.length, "category"));
    }

    @RolesAllowed({Privilege.REPORT_LIST_VIEW})
    @GetMapping("/product")
    public HttpEntity<byte[]> getProductReport(@PathVariable String format, PageRequest pageRequest, @RequestParam(value = "", required = false) Map<String, Object> filter) {
        byte[] data = reportService.getProducts(pageRequest, format, filter);
        return new HttpEntity<>(data, getResultHeader(format, data.length, "product"));
    }

    @RolesAllowed({Privilege.REPORT_LIST_VIEW})
    @GetMapping("/purchase-order-item")
    public HttpEntity<byte[]> getPurchaseOrderItem(@PathVariable String format, PageRequest pageRequest, @RequestParam(value = "", required = false) Map<String, Object> filter) {
        byte[] data = reportService.getPurchaseOrderItems(pageRequest, format, filter);
        return new HttpEntity<>(data, getResultHeader(format, data.length, "purchase_order_item"));
    }

    @RolesAllowed({Privilege.REPORT_LIST_VIEW})
    @GetMapping("/purchase-order")
    public HttpEntity<byte[]> getPurchaseOrder(@PathVariable String format, PageRequest pageRequest, @RequestParam(value = "", required = false) Map<String, Object> filter) {
        byte[] data = reportService.getPurchaseOrders(pageRequest, format, filter);
        return new HttpEntity<>(data, getResultHeader(format, data.length, "purchase_order"));
    }

    @RolesAllowed({Privilege.REPORT_LIST_VIEW})
    @GetMapping("/unit")
    public HttpEntity<byte[]> getUnit(@PathVariable String format, PageRequest pageRequest, @RequestParam(value = "", required = false) Map<String, Object> filter) {
        byte[] data = reportService.getUnits(pageRequest, format, filter);
        return new HttpEntity<>(data, getResultHeader(format, data.length, "unit"));
    }

    @RolesAllowed({Privilege.REPORT_LIST_VIEW})
    @GetMapping("/performance-staff")
    public HttpEntity<byte[]> getStaffPerformance(@PathVariable String format) {
        byte[] data = reportService.getStaffPerformances(format);
        return new HttpEntity<>(data, getResultHeader(format, data.length, "staff_performance"));
    }

    private HttpHeaders getResultHeader(String format, int len, String basename) {
        HttpHeaders header = new HttpHeaders();
        String name = String.format("inline; filename=%s%s.%s", basename, RandomStringUtils.randomAlphanumeric(6), format);
        header.set(HttpHeaders.CONTENT_DISPOSITION, name);
        header.setContentLength(len);
        header.setContentType(mediaFormat.getOrDefault(format, MediaType.APPLICATION_OCTET_STREAM));
        return header;
    }

}
