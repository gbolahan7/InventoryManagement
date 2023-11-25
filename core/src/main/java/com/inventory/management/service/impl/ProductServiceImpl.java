package com.inventory.management.service.impl;

import com.inventory.management.domain.Product;
import com.inventory.management.domain.ProductRequest;
import com.inventory.management.domain.PurchaseOrder;
import com.inventory.management.domain.PurchaseOrderRequest;
import com.inventory.management.mapper.ProductAuditMapper;
import com.inventory.management.mapper.ProductMapper;
import com.inventory.management.mapper.ProductRequestMapper;
import com.inventory.management.operation.access.AccessOperationRequest;
import com.inventory.management.operation.audit.ViewAuditOperationRequest;
import com.inventory.management.operation.core.product.approve.ApproveProductRequestOperation;
import com.inventory.management.operation.core.product.create.CreateProductOperation;
import com.inventory.management.operation.core.product.delete.DeleteProductOperation;
import com.inventory.management.operation.core.product.list.ListProductAuditOperation;
import com.inventory.management.operation.core.product.list.ListProductOperation;
import com.inventory.management.operation.core.product.list.ListProductRequestOperation;
import com.inventory.management.operation.core.product.reject.RejectProductRequestOperation;
import com.inventory.management.operation.core.product.view.ViewProductAuditOperation;
import com.inventory.management.operation.core.product.view.ViewProductOperation;
import com.inventory.management.operation.core.product.view.ViewProductRequestOperation;
import com.inventory.management.operation.create.CreateOperationRequest;
import com.inventory.management.operation.create.CreateOperationResponse;
import com.inventory.management.operation.delete.DeleteOperationRequest;
import com.inventory.management.operation.delete.DeleteOperationResponse;
import com.inventory.management.operation.list.ListAuditOperationRequest;
import com.inventory.management.operation.list.ListOperationRequest;
import com.inventory.management.operation.view.ViewOperationRequest;
import com.inventory.management.service.ProductService;
import com.inventory.management.vo.dto.ProductAuditDto;
import com.inventory.management.vo.dto.ProductDto;
import com.inventory.management.vo.dto.ProductRequestDto;
import com.inventory.management.vo.dto.PurchaseOrderDto;
import com.inventory.management.vo.problem.CustomApiException;
import com.inventory.management.vo.problem.ValidationBuilder;
import com.inventory.management.vo.problem.ValidationException;
import com.inventory.management.vo.request.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ViewProductOperation viewProductOperation;
    private final ProductMapper productMapper;
    private final ListProductOperation listProductOperation;
    private final ListProductAuditOperation listProductAuditsOperation;
    private final ViewProductAuditOperation viewProductAuditOperation;
    private final ProductAuditMapper productAuditResponseMapper;
    private final ProductRequestMapper productRequestMapper;
    private final ViewProductRequestOperation viewProductRequestOperation;
    private final ListProductRequestOperation listProductRequestsOperation;
    private final CreateProductOperation createProductOperation;
    private final DeleteProductOperation deleteProductOperation;
    private final ApproveProductRequestOperation approveProductRequestOperation;
    private final RejectProductRequestOperation rejectProductRequestOperation;

    @Override
    public Page<ProductDto> getProducts(PageRequest pageRequest, Map<String, Object> productFilter) {
        return listProductOperation.process(new ListOperationRequest(pageRequest, productFilter))
                .getPage()
                .map(productMapper::toDto);
    }

    @Override
    public ProductDto getProduct(Long id) {
        return viewProductOperation.process(new ViewOperationRequest<>(id))
                .getEntity().map(productMapper::toDto)
                .orElseThrow(() -> new ValidationException( new ValidationBuilder().addError("validation.error.entity.does.not.exist").build()));
    }

    @Override
    public ProductDto deleteProduct(Long id) {
        Product product = viewProductOperation.process(new ViewOperationRequest<>(id)).getEntity()
                .orElseThrow(() -> new ValidationException( new ValidationBuilder().addError("validation.error.entity.does.not.exist").build()));
        ProductRequest productRequest = new ProductRequest();
        BeanUtils.copyProperties(product, productRequest);
        productRequest.setProductId(product.getId());
        DeleteOperationRequest<Product, ProductRequest> request = new DeleteOperationRequest<>(product, productRequest);
        DeleteOperationResponse<Product> operationResponse = deleteProductOperation.process(request);
        return productMapper.toDto(operationResponse.getDomain());
    }

    @Override
    public ProductAuditDto getProductAudit(Long id, Integer revisionId) {
        return viewProductAuditOperation.process(new ViewAuditOperationRequest<>(id, revisionId))
                .getAuditedEntity()
                .map(productAuditResponseMapper::toDto)
                .orElseThrow(() -> new CustomApiException("error has occurred"));
    }

    @Override
    public Page<ProductAuditDto> getProductAudits(Long id, PageRequest pageRequest, Map<String, Object> productAuditsFilter) {
        return listProductAuditsOperation.process(new ListAuditOperationRequest<>(pageRequest, productAuditsFilter, id))
                .getPage().map(productAuditResponseMapper::toDto);
    }

    @Override
    public Page<ProductRequestDto> getProductRequests(PageRequest pageRequest, Map<String, Object> currencyRequestFilter) {
        return listProductRequestsOperation
                .process(new ListOperationRequest(pageRequest, currencyRequestFilter))
                .getPage().map(productRequestMapper::toDto);
    }

    @Override
    public ProductRequestDto getProductRequest(Long requestId) {
        return viewProductRequestOperation.process(new ViewOperationRequest<>(requestId))
                .getEntity().map(productRequestMapper::toDto).orElseThrow(() -> new CustomApiException("not found"));
    }

    @Override
    public Long postProductRequest(ProductRequestDto productRequestBody) {
        CreateOperationRequest<ProductRequest> request = new CreateOperationRequest<>(productRequestMapper.toEntity(productRequestBody));
        CreateOperationResponse<ProductRequest> operationResponse = createProductOperation.process(request);
        return operationResponse.getRequest().getRequestId();
    }

    @Override
    public ProductRequestDto approveProductRequest(Long requestId) {
        return productRequestMapper.toDto(approveProductRequestOperation.process(new AccessOperationRequest<>(requestId)).getEntity());
    }

    @Override
    public ProductRequestDto rejectProductRequest(Long requestId) {
        return productRequestMapper.toDto(rejectProductRequestOperation.process(new AccessOperationRequest<>(requestId)).getEntity());
    }


}
