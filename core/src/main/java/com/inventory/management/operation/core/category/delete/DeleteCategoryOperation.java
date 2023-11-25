package com.inventory.management.operation.core.category.delete;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.Category;
import com.inventory.management.domain.CategoryRequest;
import com.inventory.management.operation.delete.DeleteOperation;
import com.inventory.management.repository.CategoryRepository;
import com.inventory.management.repository.CategoryRequestRepository;

@Operation
public class DeleteCategoryOperation extends DeleteOperation<Category, Long, CategoryRepository, CategoryRequest, Long, CategoryRequestRepository> {
    public DeleteCategoryOperation(CategoryRepository categoryRepository, CategoryRequestRepository repository) {
        super(categoryRepository, repository);
    }
}
