package com.inventory.management.vo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
public class PerformanceSettingDto extends BaseAuditDto {
    private Long id;
    private String name;
    private Instant staffStartTime;
    private Instant staffStopTime;
    private String staffPeriod;
    private String staffUpdateType;
    private Double staffBonusPoint;
}
