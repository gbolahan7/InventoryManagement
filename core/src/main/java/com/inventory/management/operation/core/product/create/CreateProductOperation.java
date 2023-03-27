package com.inventory.management.operation.core.product.create;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.ProductRequest;
import com.inventory.management.operation.create.CreateOperation;
import com.inventory.management.repository.ProductRequestRepository;
import com.inventory.management.validation.ProductRequestValidator;

@Operation
public class CreateProductOperation extends CreateOperation<ProductRequest, Long, ProductRequestRepository> {
    public CreateProductOperation(ProductRequestRepository repository, ProductRequestValidator validator) {
        super(repository, validator);
    }
}
