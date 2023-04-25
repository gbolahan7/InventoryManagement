package com.inventory.management.operation.core.category.reject;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.CategoryRequest;
import com.inventory.management.operation.access.AccessOperation;
import com.inventory.management.repository.CategoryRequestRepository;
import static com.inventory.management.util.Constant.REJECTED;

@Operation
public class RejectCategoryRequestOperation extends AccessOperation<CategoryRequest, Long, CategoryRequestRepository> {
    public RejectCategoryRequestOperation(CategoryRequestRepository repository) {
        super(repository);
    }

    @Override
    protected CategoryRequest operate(CategoryRequest requestEntity) {
        requestEntity.setRequestStatus(REJECTED);
        return repository.save(requestEntity);
    }
}
