package com.inventory.management.operation.core.product.approve;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.Product;
import com.inventory.management.domain.ProductRequest;
import com.inventory.management.operation.access.AccessOperation;
import com.inventory.management.repository.ProductRepository;
import com.inventory.management.repository.ProductRequestRepository;
import com.inventory.management.service.AttachmentService;
import com.inventory.management.validation.ProductRequestValidator;
import com.inventory.management.vo.problem.ValidationBuilder;
import com.inventory.management.vo.problem.ValidationException;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static com.inventory.management.util.Constant.*;

@Operation
public class ApproveProductRequestOperation extends AccessOperation<ProductRequest, Long, ProductRequestRepository> {

    private final ProductRepository productRepository;
    private final ProductRequestValidator productRequestValidator;
    private final AttachmentService attachmentService;
    private final Map<String, Function<ProductRequest, Product>> handlers = getApproveHandlers();

    public ApproveProductRequestOperation(ProductRequestRepository productRequestRepository,
                                          ProductRepository productRepository, ProductRequestValidator productRequestValidator,
                                          AttachmentService attachmentService) {
        super(productRequestRepository);
        this.productRepository = productRepository;
        this.productRequestValidator = productRequestValidator;
        this.attachmentService = attachmentService;
    }

    @Override
    protected ProductRequest operate(ProductRequest requestEntity) {
        Product productEntity = handlers.get(requestEntity.getRequestType()).apply(requestEntity);
        productRepository.save(productEntity);
        requestEntity.setRequestStatus(APPROVED);
        return repository.save(requestEntity);
    }

    private Product handleCreate(ProductRequest request) {
        productRequestValidator.validate(CREATE, request);
        Product product = new Product();
        BeanUtils.copyProperties(request, product);
        if(StringUtils.hasText(request.getPicturePayload())) {
            product.setPictureUrl(attachmentService.saveOrUpdateFile(null, request.getPicturePayload()));
        }
        product.setVersion(UUID.randomUUID().toString());
        return product;
    }

    private Product handleModify(ProductRequest request) {
        productRequestValidator.validate(MODIFY, request);
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ValidationException(new ValidationBuilder().addError("validation.error.entity.does.not.exist").build()));
        product.setStatus(request.getStatus());
        product.setVersion(UUID.randomUUID().toString());
        product.setBrand(request.getBrand());
        product.setDiscount(request.getDiscount());
        product.setQuantity(request.getQuantity());
        product.setExpiryDate(request.getExpiryDate());
        product.setManufacturedDate(request.getManufacturedDate());
        product.setUnit(request.getUnit());
        product.setUnitPrice(request.getUnitPrice());
        product.setWarehouse(request.getWarehouse());
        product.setWarehousePrice(request.getWarehousePrice());
        if(StringUtils.hasText(request.getPicturePayload())) {
            product.setPictureUrl(attachmentService.saveOrUpdateFile(product.getPictureUrl(), request.getPicturePayload()));
        }
        return product;
    }

    private Map<String, Function<ProductRequest, Product>> getApproveHandlers() {
        Map<String, Function<ProductRequest, Product>> approveHandlers = new HashMap<>();
        approveHandlers.put(CREATE, this::handleCreate);
        approveHandlers.put(MODIFY, this::handleModify);
        return approveHandlers;
    }
}
