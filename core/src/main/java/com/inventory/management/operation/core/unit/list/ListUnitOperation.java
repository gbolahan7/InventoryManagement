package com.inventory.management.operation.core.unit.list;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.Unit;
import com.inventory.management.operation.core.unit.list.query.UnitQueryMapper;
import com.inventory.management.operation.list.ListOperation;
import com.inventory.management.repository.UnitRepository;
import com.inventory.management.util.PageRequestHelper;
import com.querydsl.core.types.Predicate;

import java.util.Map;

@Operation
public class ListUnitOperation extends ListOperation<Unit, Long, UnitRepository> {

    public ListUnitOperation(UnitRepository repository, PageRequestHelper pageRequestHelper) {
        super(repository, pageRequestHelper, Unit.class, "id");
    }

    @Override
    public Predicate getPredicate(Map<String, Object> filter) {
        return UnitQueryMapper.toPredicate(filter);
    }
}
