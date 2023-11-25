package com.inventory.management.validation;

import com.inventory.management.annotation.Validate;
import com.inventory.management.domain.Product;
import com.inventory.management.domain.ProductRequest;
import com.inventory.management.repository.ProductRepository;
import com.inventory.management.util.Constant;
import com.inventory.management.vo.problem.ValidationBuilder;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

@Validate
@AllArgsConstructor
public class ProductRequestValidator implements Validator<String, ProductRequest> {

    final private ProductRepository productRepository;

    private final Map<String, Consumer<ProductRequest>> validators = registerValidators();

    private static void buildBasicValidation(ProductRequest request, ValidationBuilder builder) {
        if (Objects.isNull(request))
            builder.addError("validation.error.product.empty");
        else {
            if (!StringUtils.hasText(request.getName()))
                builder.addError("validation.error.product.name.empty");
            if (!StringUtils.hasText(request.getCode()))
                builder.addError("validation.error.product.code.empty");
            if (!StringUtils.hasText(request.getCategory()))
                builder.addError("validation.error.product.category.empty");
            if (!StringUtils.hasText(request.getUnit()))
                builder.addError("validation.error.product.unit.empty");
            if (Objects.isNull(request.getUnitPrice()))
                builder.addError("validation.error.product.price.empty");
            if (Objects.isNull(request.getQuantity()))
                builder.addError("validation.error.product.quantity.empty");
            if((StringUtils.hasText(request.getWarehouse()) && Objects.isNull(request.getWarehousePrice())) ||
                    (!StringUtils.hasText(request.getWarehouse()) && Objects.nonNull(request.getWarehousePrice())) )
                builder.addError("validation.error.product.warehouse.empty");
            if (!StringUtils.hasText(request.getStatus()))
                builder.addError("validation.error.product.status.empty");
            else if (!request.getStatus().equals("Active") && !request.getStatus().equals("Inactive")) {
                builder.addError("validation.error.product.status.allowed");
            }
        }
    }

    @Override
    public Map<String, Consumer<ProductRequest>> getOperationValidators() {
        return validators;
    }

    private Map<String, Consumer<ProductRequest>> registerValidators() {
        Map<String, Consumer<ProductRequest>> validators = new HashMap<>();
        validators.put(Constant.CREATE, getCreationValidation());
        validators.put(Constant.MODIFY, getModifyValidation());
        return Map.copyOf(validators);
    }

    private Consumer<ProductRequest> getCreationValidation() {
        return (request) -> {
            ValidationBuilder builder = new ValidationBuilder();
            buildBasicValidation(request, builder);
            if (builder.isClean()) {
                if (productRepository.findByName(request.getName()).isPresent())
                    builder.addError("validation.error.product.name");
            }
            handleValidatorError(builder.build());
        };
    }

    private Consumer<ProductRequest> getModifyValidation() {
        return (request) -> {
            ValidationBuilder builder = new ValidationBuilder();
            buildBasicValidation(request, builder);
            if (builder.isClean()) {
                Optional<Product> optionalProduct = productRepository.findById(request.getProductId());
                if (optionalProduct.isEmpty())
                    builder.addError("validation.error.product.request");
                else{
                     Product product = optionalProduct.get();
                     if(!product.getCode().equals(request.getCode()))
                         builder.addError("validation.error.product.code.change");
                    if(!product.getName().equals(request.getName()))
                        builder.addError("validation.error.product.name.change");
                }
            }
            handleValidatorError(builder.build());
        };
    }

}
