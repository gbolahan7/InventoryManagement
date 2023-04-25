package com.inventory.management.validation;

import com.inventory.management.annotation.Validate;
import com.inventory.management.domain.Product;
import com.inventory.management.domain.PurchaseOrderRequest;
import com.inventory.management.repository.ProductRepository;
import com.inventory.management.repository.ProductRequestRepository;
import com.inventory.management.repository.PurchaseOrderRepository;
import com.inventory.management.util.Constant;
import com.inventory.management.vo.dto.PurchaseOrderItemDto;
import com.inventory.management.vo.problem.ValidationBuilder;
import com.inventory.management.vo.problem.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Validate
@AllArgsConstructor
public class PurchaseOrderRequestValidator implements Validator<String, PurchaseOrderRequest> {

    final private PurchaseOrderRepository purchaseOrderRepository;
    final private ProductRepository productRepository;

    private final Map<String, Consumer<PurchaseOrderRequest>> validators = registerValidators();
    private final ProductRequestRepository productRequestRepository;

    private static void buildBasicValidation(PurchaseOrderRequest request, ValidationBuilder builder) {
        if (Objects.isNull(request))
            builder.addError("validation.error.purchase.order.empty");
        else {
            if (!StringUtils.hasText(request.getStatus()))
                builder.addError("validation.error.purchase.order.status.empty");
            else if (!request.getStatus().equals("Paid") && !request.getStatus().equals("Pending")) {
                builder.addError("validation.error.purchase.order.status.allowed");
            }
        }
    }

    public void validateOrderItems(List<PurchaseOrderItemDto> items) {
        ValidationBuilder builder = new ValidationBuilder();
        Set<String> container = new HashSet<>();
        AtomicInteger atomicIncrement = new AtomicInteger();
        for(PurchaseOrderItemDto item: items) {
            if(item.getQuantity() == null)
                builder.addError("validation.error.purchase.order.item.quantity");
            if(item.getVatEnabled() == null)
                builder.addError("validation.error.purchase.order.item.vat");
            if(!StringUtils.hasText(item.getProductCode()))
                builder.addError("validation.error.purchase.order.item.code");
            if(!container.add(item.getProductCode())) builder.addError("validation.error.purchase.order.item.code.is.duplicated");
            Product product = productRepository.findByCode(item.getProductCode()).orElseGet(() -> {
                builder.addError("validation.error.purchase.order.item.code.not.found" , item.getProductCode());
                return null;
            });
            if(product != null && item.getQuantity() != null && item.getQuantity() > product.getQuantity()) {
                builder.addError("validation.error.purchase.order.item.product.greater.quantity", item.getQuantity(), product.getQuantity());
            }
            if(!builder.isClean()) builder.addError("validation.error.occurred.at", atomicIncrement.getAndIncrement());
        }
        if(!builder.isClean()) throw new ValidationException(builder.build());
    }

    @Override
    public Map<String, Consumer<PurchaseOrderRequest>> getOperationValidators() {
        return validators;
    }

    private Map<String, Consumer<PurchaseOrderRequest>> registerValidators() {
        Map<String, Consumer<PurchaseOrderRequest>> validators = new HashMap<>();
        validators.put(Constant.CREATE, getCreationValidation());
        validators.put(Constant.MODIFY, getModifyValidation());
        return Map.copyOf(validators);
    }

    private Consumer<PurchaseOrderRequest> getCreationValidation() {
        return (request) -> {
            ValidationBuilder builder = new ValidationBuilder();
            buildBasicValidation(request, builder);
            handleValidatorError(builder.build());
        };
    }

    private Consumer<PurchaseOrderRequest> getModifyValidation() {
        return (request) -> {
            ValidationBuilder builder = new ValidationBuilder();
            buildBasicValidation(request, builder);
            if (builder.isClean()) {
                if (purchaseOrderRepository.findById(request.getPurchaseOrderId()).isEmpty())
                    builder.addError("validation.error.purchase.order.request");
            }
            handleValidatorError(builder.build());
        };
    }

}
