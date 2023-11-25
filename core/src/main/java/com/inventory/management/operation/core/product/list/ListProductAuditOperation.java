package com.inventory.management.operation.core.product.list;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.Product;
import com.inventory.management.operation.core.product.list.query.ProductAuditQueryMapper;
import com.inventory.management.operation.list.ListAuditOperation;
import com.inventory.management.util.PageRequestHelper;
import com.inventory.management.util.audit.JpaAuditService;
import org.hibernate.envers.query.criteria.AuditCriterion;

import java.util.List;
import java.util.Map;

import static org.hibernate.envers.query.AuditEntity.property;

@Operation
public class ListProductAuditOperation extends ListAuditOperation<Product, Long> {

    public ListProductAuditOperation(JpaAuditService service, PageRequestHelper pageRequestMapper) {
        super(service, pageRequestMapper, Product.class);
    }

    @Override
    protected List<AuditCriterion> getAuditCriterionList(Map<String, Object> filter, Long identifier) {
        List<AuditCriterion> auditCriterionList = ProductAuditQueryMapper.getAuditCriterionList(filter);
        auditCriterionList.add(property("id").eq(identifier));
        return auditCriterionList;
    }
}
