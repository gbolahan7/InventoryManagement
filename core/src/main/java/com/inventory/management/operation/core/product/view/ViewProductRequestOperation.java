package com.inventory.management.operation.core.product.view;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.ProductRequest;
import com.inventory.management.operation.view.ViewOperation;
import com.inventory.management.repository.ProductRequestRepository;

@Operation
public class ViewProductRequestOperation extends ViewOperation<ProductRequest, Long, ProductRequestRepository> {

    public ViewProductRequestOperation(ProductRequestRepository repository) {
        super(repository);
    }
}

