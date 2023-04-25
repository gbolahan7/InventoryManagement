package com.inventory.management.operation.core.product.delete;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.Product;
import com.inventory.management.domain.ProductRequest;
import com.inventory.management.operation.delete.DeleteOperation;
import com.inventory.management.repository.ProductRepository;
import com.inventory.management.repository.ProductRequestRepository;

@Operation
public class DeleteProductOperation extends DeleteOperation<Product, Long, ProductRepository, ProductRequest, Long, ProductRequestRepository> {
    public DeleteProductOperation(ProductRepository productRepository, ProductRequestRepository repository) {
        super(productRepository, repository);
    }
}
