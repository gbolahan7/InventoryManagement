package com.inventory.management.operation.core.unit.view;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.UnitRequest;
import com.inventory.management.operation.view.ViewOperation;
import com.inventory.management.repository.UnitRequestRepository;

@Operation
public class ViewUnitRequestOperation extends ViewOperation<UnitRequest, Long, UnitRequestRepository> {

    public ViewUnitRequestOperation(UnitRequestRepository repository) {
        super(repository);
    }
}

