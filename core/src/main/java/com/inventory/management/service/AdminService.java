package com.inventory.management.service;

import com.inventory.management.vo.dto.RoleDto;
import com.inventory.management.vo.dto.UserDto;
import com.inventory.management.vo.request.PageRequest;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface AdminService {

    Long createRole(RoleDto roleDto);
    Page<RoleDto> getRoles(PageRequest pageRequest);
    Set<String> getPermissions();
    RoleDto getRole(Long id);
    Long modifyRole(RoleDto roleDto);
    Long modifyUser(UserDto userBody);
    Page<UserDto> getUsers(PageRequest pageRequest);
    UserDto getUser(Long id);
}
