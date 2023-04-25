package com.inventory.management.operation.core.performanceSetting.view;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.PerformanceSetting;
import com.inventory.management.operation.view.ViewAuditOperation;
import com.inventory.management.util.audit.AuditService;

@Operation
public class ViewPerformanceSettingAuditOperation extends ViewAuditOperation<PerformanceSetting, Long> {
    public ViewPerformanceSettingAuditOperation(AuditService service) {
        super(service, PerformanceSetting.class, "id");
    }
}
