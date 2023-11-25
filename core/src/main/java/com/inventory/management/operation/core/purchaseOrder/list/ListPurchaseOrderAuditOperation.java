package com.inventory.management.operation.core.purchaseOrder.list;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.PurchaseOrder;
import com.inventory.management.operation.core.purchaseOrder.list.query.PurchaseOrderAuditQueryMapper;
import com.inventory.management.operation.list.ListAuditOperation;
import com.inventory.management.util.PageRequestHelper;
import com.inventory.management.util.audit.JpaAuditService;
import org.hibernate.envers.query.criteria.AuditCriterion;

import java.util.List;
import java.util.Map;

import static org.hibernate.envers.query.AuditEntity.property;

@Operation
public class ListPurchaseOrderAuditOperation extends ListAuditOperation<PurchaseOrder, Long> {

    public ListPurchaseOrderAuditOperation(JpaAuditService service, PageRequestHelper pageRequestMapper) {
        super(service, pageRequestMapper, PurchaseOrder.class);
    }

    @Override
    protected List<AuditCriterion> getAuditCriterionList(Map<String, Object> filter, Long identifier) {
        List<AuditCriterion> auditCriterionList = PurchaseOrderAuditQueryMapper.getAuditCriterionList(filter);
        auditCriterionList.add(property("id").eq(identifier));
        return auditCriterionList;
    }
}
