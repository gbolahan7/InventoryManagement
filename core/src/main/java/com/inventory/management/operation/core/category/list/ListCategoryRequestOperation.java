package com.inventory.management.operation.core.category.list;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.CategoryRequest;
import com.inventory.management.operation.core.category.list.query.CategoryRequestQueryMapper;
import com.inventory.management.operation.list.ListOperation;
import com.inventory.management.repository.CategoryRequestRepository;
import com.inventory.management.util.PageRequestHelper;
import com.querydsl.core.types.Predicate;

import java.util.Map;

@Operation
public class ListCategoryRequestOperation extends ListOperation<CategoryRequest, Long, CategoryRequestRepository> {

    public ListCategoryRequestOperation(CategoryRequestRepository repository, PageRequestHelper pageRequestMapper) {
        super(repository, pageRequestMapper, CategoryRequest.class, "requestId");
    }

    @Override
    protected Predicate getPredicate(Map<String, Object> filter) {
        return CategoryRequestQueryMapper.toPredicate(filter);
    }
}
