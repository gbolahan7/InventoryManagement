package com.inventory.management.operation.core.product.list;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.Product;
import com.inventory.management.operation.core.product.list.query.ProductQueryMapper;
import com.inventory.management.operation.list.ListOperation;
import com.inventory.management.repository.ProductRepository;
import com.inventory.management.util.PageRequestHelper;
import com.querydsl.core.types.Predicate;

import java.util.Map;

@Operation
public class ListProductOperation extends ListOperation<Product, Long, ProductRepository> {

    public ListProductOperation(ProductRepository repository, PageRequestHelper pageRequestHelper) {
        super(repository, pageRequestHelper, Product.class, "id");
    }

    @Override
    public Predicate getPredicate(Map<String, Object> filter) {
        return ProductQueryMapper.toPredicate(filter);
    }
}
