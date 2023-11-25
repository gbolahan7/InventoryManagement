package com.inventory.management.service;

import com.inventory.management.vo.request.PageRequest;

import java.util.Map;

public interface ReportService {

    byte[] getCategories(PageRequest pageRequest, String format, Map<String, Object> filter);
    byte[] getProducts(PageRequest pageRequest, String format, Map<String, Object> filter);
    byte[] getPurchaseOrderItems(PageRequest pageRequest, String format, Map<String, Object> filter);
    byte[] getPurchaseOrders(PageRequest pageRequest, String format, Map<String, Object> filter);
    byte[] getUnits(PageRequest pageRequest, String format, Map<String, Object> filter);
    byte[] getStaffPerformances(String format);
}
