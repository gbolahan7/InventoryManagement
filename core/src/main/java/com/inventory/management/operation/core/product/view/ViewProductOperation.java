package com.inventory.management.operation.core.product.view;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.Product;
import com.inventory.management.operation.view.ViewOperation;
import com.inventory.management.repository.ProductRepository;

@Operation
public class ViewProductOperation extends ViewOperation<Product, Long, ProductRepository> {

    public ViewProductOperation(ProductRepository repository) {
        super(repository);
    }
}
