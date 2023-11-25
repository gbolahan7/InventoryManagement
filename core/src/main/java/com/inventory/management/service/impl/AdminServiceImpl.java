package com.inventory.management.service.impl;

import com.inventory.management.domain.Constants;
import com.inventory.management.domain.Role;
import com.inventory.management.domain.User;
import com.inventory.management.mapper.RoleMapper;
import com.inventory.management.mapper.UserMapper;
import com.inventory.management.repository.RoleRepository;
import com.inventory.management.repository.UserRepository;
import com.inventory.management.service.AdminService;
import com.inventory.management.util.Constant;
import com.inventory.management.util.PageRequestHelper;
import com.inventory.management.util.StartupHelper;
import com.inventory.management.validation.RoleValidator;
import com.inventory.management.validation.UserValidator;
import com.inventory.management.vo.dto.RoleDto;
import com.inventory.management.vo.dto.UserDto;
import com.inventory.management.vo.problem.CustomApiException;
import com.inventory.management.vo.request.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final RoleValidator roleValidator;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoleMapper roleMapper;
    private final UserMapper userMapper;
    private final PageRequestHelper pageRequestHelper;
    private final UserValidator userValidator;

    @Override
    public Long createRole(RoleDto roleDto) {
        roleValidator.validate(Constant.CREATE, roleDto);
        return roleRepository.save(roleMapper.toRole(roleDto)).getId();
    }

    @Override
    public Page<RoleDto> getRoles(PageRequest pageRequest) {
        return roleRepository.findAll(pageRequestHelper.map(pageRequest, Role.class, "id")).map(roleMapper::toDto);
    }

    @Override
    public Set<String> getPermissions() {
        return StartupHelper.getAllPrivileges();
    }

    @Override
    public RoleDto getRole(Long id) {
        return roleRepository.findById(id)
                .map(roleMapper::toDto)
                .orElseThrow(() -> new CustomApiException("custom.error.role.not.exist", new Object[0]));
    }

    @Override
    public Long modifyRole(RoleDto roleDto) {
        roleValidator.validate(Constant.MODIFY, roleDto);
        return roleRepository.save(roleMapper.toRole(roleDto)).getId();
    }

    @Override
    public Long modifyUser(UserDto userBody) {
        userValidator.validate(Constant.MODIFY, userBody);
        User user = userRepository.findById(userBody.getId()).orElseThrow();
        user.setStatus(Constants.Status.valueOf(userBody.getStatus().toUpperCase()));
        RoleDto roleDto = userBody.getRoles().stream().findFirst().orElseThrow();
        Role role = roleRepository.findById(roleDto.getId()).orElseThrow();

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
        role.getUsers().add(user);
        roleRepository.save(role);
        return user.getId();
    }

    @Override
    public Page<UserDto> getUsers(PageRequest pageRequest) {
        return userRepository.findAll(pageRequestHelper.map(pageRequest, User.class, "id")).map(userMapper::toDto);
    }

    @Override
    public UserDto getUser(Long id) {
        return userRepository.findDeepById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new CustomApiException("custom.error.entity.not.exist", new Object[0]));
    }
}
