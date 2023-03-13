package com.inventory.management.operation.core.category.view;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.Category;
import com.inventory.management.operation.view.ViewOperation;
import com.inventory.management.repository.CategoryRepository;

@Operation
public class ViewCategoryOperation extends ViewOperation<Category, Long, CategoryRepository> {

    public ViewCategoryOperation(CategoryRepository repository) {
        super(repository);
    }
}
