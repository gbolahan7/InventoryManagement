package com.inventory.management.vo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class RoleDto {
    private Long id;
    private String name;
    private String description;
    private List<String> permissions;
}
