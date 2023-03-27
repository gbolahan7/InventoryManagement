package com.inventory.management.operation.core.product.list;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.ProductRequest;
import com.inventory.management.operation.core.product.list.query.ProductRequestQueryMapper;
import com.inventory.management.operation.list.ListOperation;
import com.inventory.management.repository.ProductRequestRepository;
import com.inventory.management.util.PageRequestHelper;
import com.querydsl.core.types.Predicate;

import java.util.Map;

@Operation
public class ListProductRequestOperation extends ListOperation<ProductRequest, Long, ProductRequestRepository> {

    public ListProductRequestOperation(ProductRequestRepository repository, PageRequestHelper pageRequestMapper) {
        super(repository, pageRequestMapper, ProductRequest.class, "requestId");
    }

    @Override
    protected Predicate getPredicate(Map<String, Object> filter) {
        return ProductRequestQueryMapper.toPredicate(filter);
    }
}
