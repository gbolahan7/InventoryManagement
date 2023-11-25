package com.inventory.management.operation.core.performanceSetting.create;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.PerformanceSettingRequest;
import com.inventory.management.operation.create.CreateOperation;
import com.inventory.management.operation.create.CreateOperationRequest;
import com.inventory.management.operation.create.CreateOperationResponse;
import com.inventory.management.repository.PerformanceSettingRequestRepository;
import com.inventory.management.validation.PerformanceSettingRequestValidator;
import com.inventory.management.vo.problem.ValidationBuilder;
import com.inventory.management.vo.problem.ValidationException;

import static com.inventory.management.util.Constant.CREATE;
import static com.inventory.management.util.Constant.MODIFY;

@Operation
public class CreatePerformanceSettingOperation extends CreateOperation<PerformanceSettingRequest, Long, PerformanceSettingRequestRepository> {
    public CreatePerformanceSettingOperation(PerformanceSettingRequestRepository repository, PerformanceSettingRequestValidator validator) {
        super(repository, validator);
    }

    public CreateOperationResponse<PerformanceSettingRequest> process(CreateOperationRequest<PerformanceSettingRequest> request) {
        PerformanceSettingRequest requestEntity = request.getRequest();
        if(requestEntity.getRequestType() != null && requestEntity.getRequestType().equals(MODIFY)) return super.process(request);
        throw new ValidationException(new ValidationBuilder().addError("validation.error.creation.not.supported").build());
    }
}
