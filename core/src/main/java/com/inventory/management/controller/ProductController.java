package com.inventory.management.controller;

import com.inventory.management.annotation.ResponseWrapper;
import com.inventory.management.auth.Privilege;
import com.inventory.management.service.ProductService;
import com.inventory.management.vo.dto.ProductAuditDto;
import com.inventory.management.vo.dto.ProductDto;
import com.inventory.management.vo.dto.ProductRequestDto;
import com.inventory.management.vo.request.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("${api.base-path:/api/v1}"+"/product")
@ResponseWrapper
public class ProductController {

    private final ProductService productService;

    @RolesAllowed({Privilege.INVENTORY_PRODUCT_VIEW})
    @GetMapping
    public ResponseEntity<Page<ProductDto>> getCategories(PageRequest pageRequest, @RequestParam(value = "", required = false)  Map<String, Object> productFilter) {
        return ResponseEntity.ok(productService.getProducts(pageRequest, productFilter));
    }

    @RolesAllowed({Privilege.INVENTORY_PRODUCT_VIEW})
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @RolesAllowed({Privilege.INVENTORY_PRODUCT_VIEW})
    @GetMapping("/audit/id/{id}/revision/{revisionId}")
    public ResponseEntity<ProductAuditDto> getProductAudit(@PathVariable Long id, @PathVariable Integer revisionId) {
        return ResponseEntity.ok(productService.getProductAudit(id, revisionId));
    }

    @RolesAllowed({Privilege.INVENTORY_PRODUCT_VIEW})
    @GetMapping("/audit/id/{id}")
    public ResponseEntity<Page<ProductAuditDto>> getProductAudits(@PathVariable Long id, PageRequest pageRequest, @RequestParam(value = "", required = false)  Map<String, Object> productAuditsFilter) {
        return ResponseEntity.ok(productService.getProductAudits(id, pageRequest, productAuditsFilter));
    }

    @RolesAllowed({Privilege.INVENTORY_PRODUCT_VIEW})
    @GetMapping("/request")
    public ResponseEntity<Page<ProductRequestDto>> getProductRequests( PageRequest pageRequest, @RequestParam(value = "", required = false)  Map<String, Object> productRequestFilter) {
        return ResponseEntity.ok(productService.getProductRequests(pageRequest, productRequestFilter));
    }

    @RolesAllowed({Privilege.INVENTORY_PRODUCT_VIEW})
    @GetMapping("/request/{requestId}")
    public ResponseEntity<ProductRequestDto> getProductRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(productService.getProductRequest(requestId));
    }

    @RolesAllowed({Privilege.INVENTORY_PRODUCT_CREATE})
    @PostMapping("/request/create")
    public ResponseEntity<Long> createProductRequest(@RequestBody ProductRequestDto productRequestBody) {
        productRequestBody.setRequestType("create");
        return postProductRequest(productRequestBody);
    }

    @RolesAllowed({Privilege.INVENTORY_PRODUCT_MODIFY})
    @PostMapping("/request/modify")
    public ResponseEntity<Long> modifyProductRequest(@RequestBody ProductRequestDto productRequestBody) {
        productRequestBody.setRequestType("modify");
        return postProductRequest(productRequestBody);
    }

    @RolesAllowed({Privilege.INVENTORY_PRODUCT_ACCESS})
    @GetMapping("/request/approve/{requestId}")
    public ResponseEntity<ProductRequestDto> approveProductRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(productService.approveProductRequest(requestId));
    }

    @RolesAllowed({Privilege.INVENTORY_PRODUCT_ACCESS})
    @GetMapping("/request/reject/{requestId}")
    public ResponseEntity<ProductRequestDto> rejectProductRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(productService.rejectProductRequest(requestId));
    }

    private ResponseEntity<Long> postProductRequest(ProductRequestDto productRequestBody) {
        long id = productService.postProductRequest(productRequestBody);
        return ResponseEntity.created(URI.create("/" + id)).body(id);
    }

}