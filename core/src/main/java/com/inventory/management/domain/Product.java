package com.inventory.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Data
@Entity
@Audited
@Table(name = "product")
public class Product extends EntityLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String category;
    @Column
    private String brand;
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
    private String pictureUrl;
    @Column
    private BigDecimal warehousePrice;
    @Column(name = "tax")
    private double taxInPercentage;
    @Column(nullable = false)
    private Long quantity;
    @Column(nullable = false)
    private String status;
    @Column(name = "version_id", nullable = false)
    @JsonIgnore
    private String version;
}