package com.inventory.management.mapper;

import com.inventory.management.domain.Role;
import com.inventory.management.domain.User;
import com.inventory.management.vo.dto.RoleDto;
import com.inventory.management.vo.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {

    User toRole(UserDto userDto);

    UserDto toDto(User user);
}
