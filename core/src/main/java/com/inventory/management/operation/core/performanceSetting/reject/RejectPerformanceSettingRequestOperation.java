package com.inventory.management.operation.core.performanceSetting.reject;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.PerformanceSettingRequest;
import com.inventory.management.operation.access.AccessOperation;
import com.inventory.management.repository.PerformanceSettingRequestRepository;

import static com.inventory.management.util.Constant.REJECTED;

@Operation
public class RejectPerformanceSettingRequestOperation extends AccessOperation<PerformanceSettingRequest, Long, PerformanceSettingRequestRepository> {
    public RejectPerformanceSettingRequestOperation(PerformanceSettingRequestRepository repository) {
        super(repository);
    }

    @Override
    protected PerformanceSettingRequest operate(PerformanceSettingRequest requestEntity) {
        requestEntity.setRequestStatus(REJECTED);
        return repository.save(requestEntity);
    }
}
