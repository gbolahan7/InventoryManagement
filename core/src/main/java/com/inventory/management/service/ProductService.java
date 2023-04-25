package com.inventory.management.service;

import com.inventory.management.vo.dto.ProductAuditDto;
import com.inventory.management.vo.dto.ProductDto;
import com.inventory.management.vo.dto.ProductRequestDto;
import com.inventory.management.vo.request.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

public interface ProductService {

    Page<ProductDto> getProducts(PageRequest pageRequest, Map<String, Object> productFilter);
    ProductDto getProduct(Long id);
    public ProductDto deleteProduct(Long id);
    ProductAuditDto getProductAudit(Long id, Integer revisionId);
    Page<ProductAuditDto> getProductAudits(Long id, PageRequest pageRequest, Map<String, Object> productAuditsFilter);
    Page<ProductRequestDto> getProductRequests(PageRequest pageRequest, Map<String, Object> currencyRequestFilter);
    ProductRequestDto getProductRequest(@PathVariable Long requestId);
    Long postProductRequest(ProductRequestDto productRequestBody);
    ProductRequestDto approveProductRequest(Long requestId);
    ProductRequestDto rejectProductRequest(Long requestId);

}
