package com.inventory.management.operation.core.unit.list;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.Unit;
import com.inventory.management.operation.core.unit.list.query.UnitAuditQueryMapper;
import com.inventory.management.operation.list.ListAuditOperation;
import com.inventory.management.util.PageRequestHelper;
import com.inventory.management.util.audit.JpaAuditService;
import org.hibernate.envers.query.criteria.AuditCriterion;

import java.util.List;
import java.util.Map;

import static org.hibernate.envers.query.AuditEntity.property;

@Operation
public class ListUnitAuditOperation extends ListAuditOperation<Unit, Long> {

    public ListUnitAuditOperation(JpaAuditService service, PageRequestHelper pageRequestMapper) {
        super(service, pageRequestMapper, Unit.class);
    }

    @Override
    protected List<AuditCriterion> getAuditCriterionList(Map<String, Object> filter, Long identifier) {
        List<AuditCriterion> auditCriterionList = UnitAuditQueryMapper.getAuditCriterionList(filter);
        auditCriterionList.add(property("id").eq(identifier));
        return auditCriterionList;
    }
}
