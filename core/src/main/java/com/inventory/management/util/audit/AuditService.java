package com.inventory.management.util.audit;

import java.util.List;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface AuditService {
    <T> DefaultAudit<T> getAuditOf(Class<T> var1, AuditCriterion var2, Integer var3);

    <T> Page<DefaultAudit<T>> getAuditsOf(Class<T> var1, List<AuditCriterion> var2, PageRequest var3, List<String> var4);
}
