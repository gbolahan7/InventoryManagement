package com.inventory.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "product_request")
public class ProductRequest extends EntityLog implements RequestBase {

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
    private String name;
    @Column(nullable = false)
    private String category;
    @Column
    private String brand;
    @Column
    @Lob
    private String picturePayload;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String unit;
    @Column(nullable = false)
    private BigDecimal unitPrice;
    @Column
    private BigDecimal discount;
    @Column
    private Instant manufacturedDate;
    @Column
    private Instant expiryDate;
    @Column
    private String warehouse;
    @Column
    private BigDecimal warehousePrice;
    @Column(name = "tax")
    private double taxInPercentage;
    @Column(nullable = false)
    private Long quantity;
    @Column
    private Long productId;
    @Column
    private String status;

    @Version
    @Column(name = "version_id", nullable = false)
    @JsonIgnore
    private int version;

}