package com.inventory.management.operation.core.unit.list;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.UnitRequest;
import com.inventory.management.operation.core.unit.list.query.UnitRequestQueryMapper;
import com.inventory.management.operation.list.ListOperation;
import com.inventory.management.repository.UnitRequestRepository;
import com.inventory.management.util.PageRequestHelper;
import com.querydsl.core.types.Predicate;

import java.util.Map;

@Operation
public class ListUnitRequestOperation extends ListOperation<UnitRequest, Long, UnitRequestRepository> {

    public ListUnitRequestOperation(UnitRequestRepository repository, PageRequestHelper pageRequestMapper) {
        super(repository, pageRequestMapper, UnitRequest.class, "requestId");
    }

    @Override
    protected Predicate getPredicate(Map<String, Object> filter) {
        return UnitRequestQueryMapper.toPredicate(filter);
    }
}
