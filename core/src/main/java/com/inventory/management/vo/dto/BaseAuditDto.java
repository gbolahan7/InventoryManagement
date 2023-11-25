package com.inventory.management.vo.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class BaseAuditDto {
    private Instant createdDate;
    private Instant modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
