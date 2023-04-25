package com.inventory.management.vo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductRequestDto extends ProductDto{
    private long requestId;
    private String requestType;
    private String requestStatus;
    private Long productId;
    private String picturePayload;
}
