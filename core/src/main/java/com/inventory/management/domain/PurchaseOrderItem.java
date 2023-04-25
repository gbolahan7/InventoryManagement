package com.inventory.management.domain;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Audited
@Table(name = "purchase_order_item")
public class PurchaseOrderItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false)
    private Long id;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private String productCode;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private Long quantity;
    @Column(nullable = false)
    private Boolean vatEnabled;
    @ManyToOne
    @JoinColumn(name = "purchase_id", referencedColumnName = "id")
    @ToString.Exclude
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "purchase_request_id", referencedColumnName = "request_id")
    @Audited(targetAuditMode = NOT_AUDITED)
    @ToString.Exclude
    private PurchaseOrderRequest purchaseOrderRequest;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PurchaseOrderItem that = (PurchaseOrderItem) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
