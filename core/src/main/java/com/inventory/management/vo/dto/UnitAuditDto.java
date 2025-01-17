package com.inventory.management.vo.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class UnitAuditDto {
    private Long id;
    private Integer revisionId;
    private Instant revisionDate;
    private String revisionType;
    private UnitDto entity;
}
