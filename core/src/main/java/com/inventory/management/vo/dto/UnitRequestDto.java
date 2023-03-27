package com.inventory.management.vo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UnitRequestDto extends UnitDto{
    private long requestId;
    private String requestType;
    private String requestStatus;
    private Long unitId;
    private String status;
}