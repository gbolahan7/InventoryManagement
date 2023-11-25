package com.inventory.management.util.audit;

import java.time.Instant;
import java.util.Objects;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;

public class RevisionMapper {
    private RevisionMapper() {
    }

    public static <T> DefaultAudit<T> toRevisionAuditedEntity(T[] auditObjects) {
        if (!Objects.isNull(auditObjects) && auditObjects.length == 3 && auditObjects[1] instanceof DefaultRevisionEntity && auditObjects[2] instanceof RevisionType) {
            DefaultAudit<T> result = new DefaultAudit<T>();
            T entity = auditObjects[0];
            DefaultRevisionEntity defaultRevisionEntity = (DefaultRevisionEntity)auditObjects[1];
            RevisionType revisionType = (RevisionType)auditObjects[2];
            result.setEntity(entity);
            result.setRevisionDate(Instant.ofEpochMilli(defaultRevisionEntity.getTimestamp()));
            result.setRevisionId(defaultRevisionEntity.getId());
            result.setRevisionType(revisionType);
            return result;
        } else {
            throw new RuntimeException("Failure mapping to AuditedEntity");
        }
    }
}
