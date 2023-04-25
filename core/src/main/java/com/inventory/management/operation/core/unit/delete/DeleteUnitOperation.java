package com.inventory.management.operation.core.unit.delete;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.Unit;
import com.inventory.management.domain.UnitRequest;
import com.inventory.management.operation.delete.DeleteOperation;
import com.inventory.management.repository.UnitRepository;
import com.inventory.management.repository.UnitRequestRepository;

@Operation
public class DeleteUnitOperation extends DeleteOperation<Unit, Long, UnitRepository, UnitRequest, Long, UnitRequestRepository> {
    public DeleteUnitOperation(UnitRepository unitRepository, UnitRequestRepository repository) {
        super(unitRepository, repository);
    }
}
