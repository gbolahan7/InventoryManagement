package com.inventory.management.operation.view;

import com.inventory.management.operation.Operation;
import com.inventory.management.operation.audit.ViewAuditOperationRequest;
import com.inventory.management.operation.audit.ViewAuditOperationResponse;
import com.inventory.management.util.audit.AuditService;
import com.inventory.management.util.audit.DefaultAudit;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

import static org.hibernate.envers.query.AuditEntity.property;

@AllArgsConstructor
public abstract class ViewAuditOperation<T, I> implements Operation<ViewAuditOperationRequest<I>, ViewAuditOperationResponse<T>> {
    private final AuditService service;
    private final Class<T> entityClass;
    private final String identifierPropertyName;

    @Override
    @Transactional(readOnly = true)
    public ViewAuditOperationResponse<T> process(ViewAuditOperationRequest<I> request) {
        DefaultAudit<T> auditedEntity;
        try {
            auditedEntity = service.getAuditOf(entityClass,
                    property(identifierPropertyName).eq(request.getIdentifier()),
                    request.getRevisionId());
        } catch (NoResultException ignored) {
            auditedEntity = null;
        }
        return new ViewAuditOperationResponse<>(auditedEntity);
    }
}
