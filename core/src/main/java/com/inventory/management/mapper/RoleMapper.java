package com.inventory.management.mapper;

import com.inventory.management.domain.Role;
import com.inventory.management.vo.dto.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(source = "permissions", target = "privileges")
    Role toRole(RoleDto roleDto);

    @Mapping(target = "permissions", source = "privileges")
    RoleDto toDto(Role role);
}
