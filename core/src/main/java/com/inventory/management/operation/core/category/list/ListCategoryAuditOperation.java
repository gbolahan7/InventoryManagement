package com.inventory.management.operation.core.category.list;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.Category;
import com.inventory.management.operation.core.category.list.query.CategoryAuditQueryMapper;
import com.inventory.management.operation.list.ListAuditOperation;
import com.inventory.management.util.PageRequestHelper;
import com.inventory.management.util.audit.JpaAuditService;
import org.hibernate.envers.query.criteria.AuditCriterion;

import java.util.List;
import java.util.Map;

import static org.hibernate.envers.query.AuditEntity.property;

@Operation
public class ListCategoryAuditOperation extends ListAuditOperation<Category, Long> {

    public ListCategoryAuditOperation(JpaAuditService service, PageRequestHelper pageRequestMapper) {
        super(service, pageRequestMapper, Category.class);
    }

    @Override
    protected List<AuditCriterion> getAuditCriterionList(Map<String, Object> filter, Long identifier) {
        List<AuditCriterion> auditCriterionList = CategoryAuditQueryMapper.getAuditCriterionList(filter);
        auditCriterionList.add(property("id").eq(identifier));
        return auditCriterionList;
    }
}
