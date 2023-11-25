package com.inventory.management.operation.core.unit.reject;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.UnitRequest;
import com.inventory.management.operation.access.AccessOperation;
import com.inventory.management.repository.UnitRequestRepository;

import static com.inventory.management.util.Constant.REJECTED;

@Operation
public class RejectUnitRequestOperation extends AccessOperation<UnitRequest, Long, UnitRequestRepository> {
    public RejectUnitRequestOperation(UnitRequestRepository repository) {
        super(repository);
    }

    @Override
    protected UnitRequest operate(UnitRequest requestEntity) {
        requestEntity.setRequestStatus(REJECTED);
        return repository.save(requestEntity);
    }
}
