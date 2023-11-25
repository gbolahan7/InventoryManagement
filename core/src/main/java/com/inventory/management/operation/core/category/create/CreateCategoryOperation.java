package com.inventory.management.operation.core.category.create;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.CategoryRequest;
import com.inventory.management.operation.create.CreateOperation;
import com.inventory.management.repository.CategoryRequestRepository;
import com.inventory.management.validation.CategoryRequestValidator;

@Operation
public class CreateCategoryOperation extends CreateOperation<CategoryRequest, Long, CategoryRequestRepository> {
    public CreateCategoryOperation(CategoryRequestRepository repository, CategoryRequestValidator validator) {
        super(repository, validator);
    }
}
