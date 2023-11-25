package com.inventory.management.operation.core.unit.approve;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.Unit;
import com.inventory.management.domain.UnitRequest;
import com.inventory.management.operation.access.AccessOperation;
import com.inventory.management.repository.UnitRepository;
import com.inventory.management.repository.UnitRequestRepository;
import com.inventory.management.validation.UnitRequestValidator;
import com.inventory.management.vo.problem.ValidationBuilder;
import com.inventory.management.vo.problem.ValidationException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static com.inventory.management.util.Constant.*;

@Operation
public class ApproveUnitRequestOperation extends AccessOperation<UnitRequest, Long, UnitRequestRepository> {

    private final UnitRepository unitRepository;
    private final UnitRequestValidator unitRequestValidator;
    private final Map<String, Function<UnitRequest, Unit>> handlers = getApproveHandlers();

    public ApproveUnitRequestOperation(UnitRequestRepository unitRequestRepository,
                                       UnitRepository unitRepository, UnitRequestValidator unitRequestValidator) {
        super(unitRequestRepository);
        this.unitRepository = unitRepository;
        this.unitRequestValidator = unitRequestValidator;
    }

    @Override
    protected UnitRequest operate(UnitRequest requestEntity) {
        Unit unitEntity = handlers.get(requestEntity.getRequestType()).apply(requestEntity);
        unitRepository.save(unitEntity);
        requestEntity.setRequestStatus(APPROVED);
        return repository.save(requestEntity);
    }

    private Unit handleCreate(UnitRequest request) {
        unitRequestValidator.validate(CREATE, request);
        Unit unit = new Unit();
        unit.setName(request.getName());
        unit.setDescription(request.getDescription());
        unit.setStatus(request.getStatus());
        unit.setVersion(UUID.randomUUID().toString());
        return unit;
    }

    private Unit handleModify(UnitRequest request) {
        unitRequestValidator.validate(MODIFY, request);
        Unit unit = unitRepository.findById(request.getUnitId())
                .orElseThrow(() -> new ValidationException(new ValidationBuilder().addError("validation.error.entity.does.not.exist").build()));
        unit.setDescription(request.getDescription());
        unit.setStatus(request.getStatus());
        unit.setVersion(UUID.randomUUID().toString());
        return unit;
    }

    private Map<String, Function<UnitRequest, Unit>> getApproveHandlers() {
        Map<String, Function<UnitRequest, Unit>> approveHandlers = new HashMap<>();
        approveHandlers.put(CREATE, this::handleCreate);
        approveHandlers.put(MODIFY, this::handleModify);
        return approveHandlers;
    }
}
