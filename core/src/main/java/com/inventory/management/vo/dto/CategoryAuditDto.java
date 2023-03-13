package com.inventory.management.vo.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class CategoryAuditDto {
    private Long id;
    private Integer revisionId;
    private Instant revisionDate;
    private String revisionType;
    private CategoryDto entity;
}
