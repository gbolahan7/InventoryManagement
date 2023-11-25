package com.inventory.management.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@Audited
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@Getter
@Setter
@ToString
public abstract class EntityLog implements Serializable {
    @Column(
            name = "created_date",
            nullable = false,
            updatable = false
    )
    @CreatedDate
    protected Instant createdDate;
    @Column(
            name = "modified_date"
    )
    @LastModifiedDate
    protected Instant modifiedDate;
    @Column(
            name = "created_by",
            updatable = false
    )
    @CreatedBy
    protected String createdBy;
    @Column(
            name = "modified_by"
    )
    @LastModifiedBy
    protected String modifiedBy;

}
