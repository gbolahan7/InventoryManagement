package com.inventory.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Audited
@Table(name = "purchase_order")
public class PurchaseOrder extends EntityLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false)
    private Long id;
    @Column(nullable = false)
    @Lob
    private String description;
    @Column(nullable = false)
    private Instant purchasedDate;
    @Column(nullable = false)
    private String status;
    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<PurchaseOrderItem> items;
    @Column(name = "version_id", nullable = false)
    @JsonIgnore
    private String version;
}
