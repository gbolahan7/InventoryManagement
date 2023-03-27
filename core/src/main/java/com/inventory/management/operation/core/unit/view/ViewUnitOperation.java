package com.inventory.management.operation.core.unit.view;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.Unit;
import com.inventory.management.operation.view.ViewOperation;
import com.inventory.management.repository.UnitRepository;

@Operation
public class ViewUnitOperation extends ViewOperation<Unit, Long, UnitRepository> {

    public ViewUnitOperation(UnitRepository repository) {
        super(repository);
    }
}
