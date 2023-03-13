package com.inventory.management.operation.core.category.view;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.Category;
import com.inventory.management.operation.view.ViewAuditOperation;
import com.inventory.management.util.audit.AuditService;
import org.springframework.stereotype.Service;

@Operation
public class ViewCategoryAuditOperation extends ViewAuditOperation<Category, Long> {
    public ViewCategoryAuditOperation(AuditService service) {
        super(service, Category.class, "id");
    }
}
