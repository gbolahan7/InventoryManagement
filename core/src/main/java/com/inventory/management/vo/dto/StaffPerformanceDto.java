package com.inventory.management.vo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventory.management.domain.EntityLog;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class StaffPerformanceDto extends BaseAuditDto{
    private Long id;
    private Boolean isNew;
    private String username;
    private String period;
    private String type;
    private Double averagePeriodicPerformance;
    private Double bonusPoint;
}
