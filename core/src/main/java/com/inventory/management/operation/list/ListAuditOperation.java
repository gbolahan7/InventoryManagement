package com.inventory.management.operation.list;

import com.inventory.management.operation.Operation;
import com.inventory.management.util.PageRequestHelper;
import com.inventory.management.util.audit.DefaultAudit;
import com.inventory.management.util.audit.JpaAuditService;
import lombok.AllArgsConstructor;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public abstract class ListAuditOperation<T, I> implements Operation<ListAuditOperationRequest<I>, ListAuditOperationResponse<T>> {
    private final JpaAuditService service;
    private final PageRequestHelper pageRequestMapper;
    private final Class<T> entityClass;
    private static final String DEFAULT_SORTING_PROPERTY = "revisionId";

    @Override
    @Transactional(readOnly = true)
    public ListAuditOperationResponse<T> process(ListAuditOperationRequest<I> request) {
        List<AuditCriterion> criterionList = getAuditCriterionList(request.getFilter(), request.getIdentifier());
        PageRequest pageRequest = pageRequestMapper.mapForAudits(request.getPageRequest(), entityClass, DEFAULT_SORTING_PROPERTY);
        Page<DefaultAudit<T>> auditPage = service.getAuditsOf(entityClass, criterionList, pageRequest, null);
        return new ListAuditOperationResponse<>(PageRequestHelper.removeSecondarySortingField(auditPage, DEFAULT_SORTING_PROPERTY));
    }

    protected abstract List<AuditCriterion> getAuditCriterionList(Map<String, Object> filter, I identifier);
}
