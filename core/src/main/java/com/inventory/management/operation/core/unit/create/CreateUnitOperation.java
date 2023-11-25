package com.inventory.management.operation.core.unit.create;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.UnitRequest;
import com.inventory.management.operation.create.CreateOperation;
import com.inventory.management.repository.UnitRequestRepository;
import com.inventory.management.validation.UnitRequestValidator;

@Operation
public class CreateUnitOperation extends CreateOperation<UnitRequest, Long, UnitRequestRepository> {
    public CreateUnitOperation(UnitRequestRepository repository, UnitRequestValidator validator) {
        super(repository, validator);
    }
}
