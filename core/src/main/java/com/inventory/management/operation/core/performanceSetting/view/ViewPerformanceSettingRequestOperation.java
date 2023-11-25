package com.inventory.management.operation.core.performanceSetting.view;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.PerformanceSettingRequest;
import com.inventory.management.operation.view.ViewOperation;
import com.inventory.management.repository.PerformanceSettingRequestRepository;

@Operation
public class ViewPerformanceSettingRequestOperation extends ViewOperation<PerformanceSettingRequest, Long, PerformanceSettingRequestRepository> {

    public ViewPerformanceSettingRequestOperation(PerformanceSettingRequestRepository repository) {
        super(repository);
    }
}

