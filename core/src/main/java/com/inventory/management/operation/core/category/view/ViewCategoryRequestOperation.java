package com.inventory.management.operation.core.category.view;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.CategoryRequest;
import com.inventory.management.operation.view.ViewOperation;
import com.inventory.management.repository.CategoryRequestRepository;

@Operation
public class ViewCategoryRequestOperation extends ViewOperation<CategoryRequest, Long, CategoryRequestRepository> {

    public ViewCategoryRequestOperation(CategoryRequestRepository repository) {
        super(repository);
    }
}

