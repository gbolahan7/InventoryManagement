package com.inventory.management.operation.core.performanceSetting.view;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.PerformanceSetting;
import com.inventory.management.operation.view.ViewOperation;
import com.inventory.management.repository.PerformanceSettingRepository;

@Operation
public class ViewPerformanceSettingOperation extends ViewOperation<PerformanceSetting, Long, PerformanceSettingRepository> {

    public ViewPerformanceSettingOperation(PerformanceSettingRepository repository) {
        super(repository);
    }
}
