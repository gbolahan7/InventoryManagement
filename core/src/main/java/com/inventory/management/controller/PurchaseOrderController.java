package com.inventory.management.controller;

import com.inventory.management.annotation.ResponseWrapper;
import com.inventory.management.auth.Privilege;
import com.inventory.management.service.PurchaseOrderService;
import com.inventory.management.vo.dto.*;
import com.inventory.management.vo.request.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.inventory.management.auth.Privilege.*;

@RestController
@AllArgsConstructor
@RequestMapping("${api.base-path:/api/v1}" + "/purchase-order")
@ResponseWrapper
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    @RolesAllowed({Privilege.INVENTORY_PURCHASE_ORDER_VIEW})
    @GetMapping
    public ResponseEntity<Page<PurchaseOrderDto>> getPurchaseOrders(PageRequest pageRequest, @RequestParam(value = "", required = false) Map<String, Object> purchaseOrderFilter) {
        return ResponseEntity.ok(purchaseOrderService.getPurchaseOrders(pageRequest, purchaseOrderFilter));
    }

    @RolesAllowed({Privilege.INVENTORY_PURCHASE_ORDER_VIEW})
    @GetMapping("/items")
    public ResponseEntity<Page<PurchaseOrderItemDto>> getPurchaseOrderItems(PageRequest pageRequest, @RequestParam(value = "", required = false) Map<String, Object> purchaseOrderItemFilter) {
        return ResponseEntity.ok(purchaseOrderService.getPurchaseOrderItems(pageRequest, purchaseOrderItemFilter));
    }

    @RolesAllowed({Privilege.INVENTORY_PURCHASE_ORDER_VIEW})
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderDto> getPurchaseOrder(@PathVariable("id") Long id) {
        return ResponseEntity.ok(purchaseOrderService.getPurchaseOrder(id));
    }

    @RolesAllowed({INVENTORY_PURCHASE_ORDER_DELETE})
    @GetMapping("/delete/{id}")
    public ResponseEntity<PurchaseOrderDto> deletePurchaseOrder(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseOrderService.deletePurchaseOrder(id));
    }

    @RolesAllowed({INVENTORY_PURCHASE_ORDER_VIEW, INVENTORY_PURCHASE_ORDER_CREATE, INVENTORY_PURCHASE_ORDER_MODIFY})
    @PostMapping("/populate/items")
    public List<PurchaseOrderItemDto> populateItems(@RequestBody List<PurchaseOrderItemDto> items) {
        return purchaseOrderService.populateItems(items);
    }

    @RolesAllowed({INVENTORY_PURCHASE_ORDER_VIEW, INVENTORY_PURCHASE_ORDER_CREATE, INVENTORY_PURCHASE_ORDER_MODIFY})
    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok(purchaseOrderService.getProducts());
    }

    @RolesAllowed({Privilege.INVENTORY_PURCHASE_ORDER_VIEW})
    @GetMapping("/audit/id/{id}/revision/{revisionId}")
    public ResponseEntity<PurchaseOrderAuditDto> getPurchaseOrderAudit(@PathVariable Long id, @PathVariable Integer revisionId) {
        return ResponseEntity.ok(purchaseOrderService.getPurchaseOrderAudit(id, revisionId));
    }

    @RolesAllowed({Privilege.INVENTORY_PURCHASE_ORDER_VIEW})
    @GetMapping("/audit/id/{id}")
    public ResponseEntity<Page<PurchaseOrderAuditDto>> getPurchaseOrderAudits(@PathVariable Long id, PageRequest pageRequest, @RequestParam(value = "", required = false) Map<String, Object> purchaseOrderAuditsFilter) {
        return ResponseEntity.ok(purchaseOrderService.getPurchaseOrderAudits(id, pageRequest, purchaseOrderAuditsFilter));
    }

    @RolesAllowed({Privilege.INVENTORY_PURCHASE_ORDER_VIEW})
    @GetMapping("/request")
    public ResponseEntity<Page<PurchaseOrderRequestDto>> getPurchaseOrderRequests(PageRequest pageRequest, @RequestParam(value = "", required = false) Map<String, Object> purchaseOrderRequestFilter) {
        return ResponseEntity.ok(purchaseOrderService.getPurchaseOrderRequests(pageRequest, purchaseOrderRequestFilter));
    }

    @RolesAllowed({Privilege.INVENTORY_PURCHASE_ORDER_VIEW})
    @GetMapping("/request/{requestId}")
    public ResponseEntity<PurchaseOrderRequestDto> getPurchaseOrderRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(purchaseOrderService.getPurchaseOrderRequest(requestId));
    }

    @RolesAllowed({Privilege.INVENTORY_PURCHASE_ORDER_CREATE})
    @PostMapping("/request/create")
    public ResponseEntity<Long> createPurchaseOrderRequest(@RequestBody PurchaseOrderRequestDto purchaseOrderRequestBody) {
        purchaseOrderRequestBody.setRequestType("create");
        return postPurchaseOrderRequest(purchaseOrderRequestBody);
    }

    @RolesAllowed({Privilege.INVENTORY_PURCHASE_ORDER_MODIFY})
    @PostMapping("/request/modify")
    public ResponseEntity<Long> modifyPurchaseOrderRequest(@RequestBody PurchaseOrderRequestDto purchaseOrderRequestBody) {
        purchaseOrderRequestBody.setRequestType("modify");
        purchaseOrderRequestBody.setItems(new ArrayList<>());
        return postPurchaseOrderRequest(purchaseOrderRequestBody);
    }

    @RolesAllowed({Privilege.INVENTORY_PURCHASE_ORDER_ACCESS})
    @GetMapping("/request/approve/{requestId}")
    public ResponseEntity<PurchaseOrderRequestDto> approvePurchaseOrderRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(purchaseOrderService.approvePurchaseOrderRequest(requestId));
    }

    @RolesAllowed({Privilege.INVENTORY_PURCHASE_ORDER_ACCESS})
    @GetMapping("/request/reject/{requestId}")
    public ResponseEntity<PurchaseOrderRequestDto> rejectPurchaseOrderRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(purchaseOrderService.rejectPurchaseOrderRequest(requestId));
    }

    private ResponseEntity<Long> postPurchaseOrderRequest(PurchaseOrderRequestDto purchaseOrderRequestBody) {
        long id = purchaseOrderService.postPurchaseOrderRequest(purchaseOrderRequestBody);
        return ResponseEntity.created(URI.create("/" + id)).body(id);
    }

}
