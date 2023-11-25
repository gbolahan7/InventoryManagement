package com.inventory.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "purchase_order_request")
public class PurchaseOrderRequest extends EntityLog implements RequestBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id", nullable = false, insertable = false)
    private long requestId;

    @Column(nullable = false)
    @Pattern(regexp = "^(create|modify|delete)$", message = "request.type.invalid.regex")
    @NotBlank(message = "field.not.blank")
    private String requestType;

    @Column(nullable = false)
    private String requestStatus;

    @Column(nullable = false)
    private String description;
    @Column
    private Long purchaseOrderId;
    @Column
    private Instant purchasedDate;
    @Column
    @Pattern(regexp = "^(Pending|Paid)$", message = "request.type.invalid.regex")
    private String status;

    @OneToMany(mappedBy = "purchaseOrderRequest", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<PurchaseOrderItem> items;

    @Version
    @Column(name = "version_id", nullable = false)
    @JsonIgnore
    private int version;

}
