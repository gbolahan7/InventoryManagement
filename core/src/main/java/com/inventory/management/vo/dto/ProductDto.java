package com.inventory.management.vo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductDto extends BaseAuditDto {
    private Long id;
    private String name;
    private String status;
    private String category;
    private String brand;
    private String code;
    private String unit;
    private BigDecimal unitPrice;
    private BigDecimal discount;
    private Instant manufacturedDate;
    private Instant expiryDate;
    private String warehouse;
    private BigDecimal warehousePrice;
    private double taxInPercentage;
    private Long quantity;
    private String pictureUrl;
}
