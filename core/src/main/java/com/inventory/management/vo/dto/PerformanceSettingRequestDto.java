package com.inventory.management.vo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PerformanceSettingRequestDto extends PerformanceSettingDto{
    private long requestId;
    private String requestType;
    private String requestStatus;
    private Long performanceSettingId;
}
