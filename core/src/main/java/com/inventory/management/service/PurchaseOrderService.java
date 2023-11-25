package com.inventory.management.service;

import com.inventory.management.vo.dto.*;
import com.inventory.management.vo.request.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface PurchaseOrderService {

    Page<PurchaseOrderDto> getPurchaseOrders(PageRequest pageRequest, Map<String, Object> purchaseOrderFilter);
    Page<PurchaseOrderItemDto> getPurchaseOrderItems(PageRequest pageRequest, Map<String, Object> purchaseOrderItemFilter);
    PurchaseOrderDto getPurchaseOrder(Long id);
    PurchaseOrderDto deletePurchaseOrder(Long id);
    PurchaseOrderAuditDto getPurchaseOrderAudit(Long id, Integer revisionId);
    Page<PurchaseOrderAuditDto> getPurchaseOrderAudits(Long id, PageRequest pageRequest, Map<String, Object> purchaseOrderAuditsFilter);
    Page<PurchaseOrderRequestDto> getPurchaseOrderRequests(PageRequest pageRequest, Map<String, Object> currencyRequestFilter);
    PurchaseOrderRequestDto getPurchaseOrderRequest(@PathVariable Long requestId);
    Long postPurchaseOrderRequest(PurchaseOrderRequestDto purchaseOrderRequestBody);
    PurchaseOrderRequestDto approvePurchaseOrderRequest(Long requestId);
    PurchaseOrderRequestDto rejectPurchaseOrderRequest(Long requestId);
    List<PurchaseOrderItemDto> populateItems(@RequestBody List<PurchaseOrderItemDto> items);
    List<ProductDto> getProducts();
}
