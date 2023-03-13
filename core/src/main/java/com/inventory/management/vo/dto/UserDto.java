package com.inventory.management.vo.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String username;
    private Set<RoleDto> roles;
    private String status;
}
