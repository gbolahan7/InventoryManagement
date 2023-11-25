package com.inventory.management.vo.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PurchaseOrderItemDto implements Serializable {
    private Long id;
    private String productName;
    private String productCode;
    private BigDecimal amount;
    private Long quantity;
    private Boolean vatEnabled;
    private Long purchaseOrderId;
    private BigDecimal totalAmount;
}
