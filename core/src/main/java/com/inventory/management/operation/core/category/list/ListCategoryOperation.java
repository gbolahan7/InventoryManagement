package com.inventory.management.operation.core.category.list;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.Category;
import com.inventory.management.operation.core.category.list.query.CategoryQueryMapper;
import com.inventory.management.operation.list.ListOperation;
import com.inventory.management.repository.CategoryRepository;
import com.inventory.management.util.PageRequestHelper;
import com.querydsl.core.types.Predicate;

import java.util.Map;

@Operation
public class ListCategoryOperation extends ListOperation<Category, Long, CategoryRepository> {

    public ListCategoryOperation(CategoryRepository repository, PageRequestHelper pageRequestHelper) {
        super(repository, pageRequestHelper, Category.class, "id");
    }

    @Override
    public Predicate getPredicate(Map<String, Object> filter) {
        return CategoryQueryMapper.toPredicate(filter);
    }
}
