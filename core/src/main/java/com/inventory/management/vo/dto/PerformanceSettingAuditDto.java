package com.inventory.management.vo.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class PerformanceSettingAuditDto {
    private Long id;
    private Integer revisionId;
    private Instant revisionDate;
    private String revisionType;
    private PerformanceSettingDto entity;
}
