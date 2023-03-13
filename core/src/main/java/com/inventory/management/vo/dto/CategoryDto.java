package com.inventory.management.vo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryDto extends BaseAuditDto {
    private Long id;
    private String name;
    private String description;
    private String status;
    private Set<String> items;
}
