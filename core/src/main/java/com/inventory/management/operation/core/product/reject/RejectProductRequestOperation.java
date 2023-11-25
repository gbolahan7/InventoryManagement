package com.inventory.management.operation.core.product.reject;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.ProductRequest;
import com.inventory.management.operation.access.AccessOperation;
import com.inventory.management.repository.ProductRequestRepository;

import static com.inventory.management.util.Constant.REJECTED;

@Operation
public class RejectProductRequestOperation extends AccessOperation<ProductRequest, Long, ProductRequestRepository> {
    public RejectProductRequestOperation(ProductRequestRepository repository) {
        super(repository);
    }

    @Override
    protected ProductRequest operate(ProductRequest requestEntity) {
        requestEntity.setRequestStatus(REJECTED);
        return repository.save(requestEntity);
    }
}
