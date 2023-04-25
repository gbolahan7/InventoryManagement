package com.inventory.management.operation.core.performanceSetting.list;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.PerformanceSetting;
import com.inventory.management.operation.core.performanceSetting.list.query.PerformanceSettingAuditQueryMapper;
import com.inventory.management.operation.list.ListAuditOperation;
import com.inventory.management.util.PageRequestHelper;
import com.inventory.management.util.audit.JpaAuditService;
import org.hibernate.envers.query.criteria.AuditCriterion;

import java.util.List;
import java.util.Map;

import static org.hibernate.envers.query.AuditEntity.property;

@Operation
public class ListPerformanceSettingAuditOperation extends ListAuditOperation<PerformanceSetting, Long> {

    public ListPerformanceSettingAuditOperation(JpaAuditService service, PageRequestHelper pageRequestMapper) {
        super(service, pageRequestMapper, PerformanceSetting.class);
    }

    @Override
    protected List<AuditCriterion> getAuditCriterionList(Map<String, Object> filter, Long identifier) {
        List<AuditCriterion> auditCriterionList = PerformanceSettingAuditQueryMapper.getAuditCriterionList(filter);
        auditCriterionList.add(property("id").eq(identifier));
        return auditCriterionList;
    }
}
