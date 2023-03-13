package com.inventory.management.util.audit;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.RevisionType;

import java.time.Instant;

@NoArgsConstructor
@Data
public class DefaultAudit<T> {
    private Integer revisionId;
    private Instant revisionDate;
    private RevisionType revisionType;
    private T entity;

}
