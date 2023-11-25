package com.inventory.management.controller;

import com.inventory.management.annotation.ResponseWrapper;
import com.inventory.management.auth.Privilege;
import com.inventory.management.service.AdminService;
import com.inventory.management.vo.dto.RoleDto;
import com.inventory.management.vo.dto.UserDto;
import com.inventory.management.vo.request.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("${api.base-path:/api/v1}"+"/admin")
@ResponseWrapper
public class AdminController {

    private final AdminService adminService;

    @RolesAllowed({Privilege.ADMIN_ROLE_OPERATION})
    @PostMapping("/role/create")
    public ResponseEntity<Long> createRole(@Valid @RequestBody RoleDto roleDto) {
        long id = adminService.createRole(roleDto);
        return  ResponseEntity.created(URI.create("/" + id)).body(id);
    }

    @RolesAllowed({Privilege.ADMIN_ROLE_OPERATION})
    @GetMapping("/role")
    public ResponseEntity<Page<RoleDto>> getRoles(PageRequest pageRequest) {
        return ResponseEntity.ok(adminService.getRoles(pageRequest));
    }

    @RolesAllowed({Privilege.ADMIN_ROLE_OPERATION})
    @GetMapping("/role/{id}")
    public ResponseEntity<RoleDto> getRole(@PathVariable("id") Long id) {
        return ResponseEntity.ok(adminService.getRole(id));
    }

    @RolesAllowed({Privilege.ADMIN_ROLE_OPERATION})
    @PostMapping("/role/modify")
    public ResponseEntity<Long> modifyRoleRequest(@RequestBody RoleDto roleRequestBody) {
        return ResponseEntity.ok(adminService.modifyRole(roleRequestBody));
    }

    @RolesAllowed({Privilege.ADMIN_ROLE_OPERATION})
    @GetMapping("/role/permission")
    public ResponseEntity<Set<String>> getRolePermissions() {
        return ResponseEntity.ok(adminService.getPermissions());
    }

    @RolesAllowed({Privilege.ADMIN_USER_OPERATION})
    @PostMapping("/user/modify")
    public ResponseEntity<Long> assignUserRole(@RequestBody UserDto userBody) {
        return ResponseEntity.ok(adminService.modifyUser(userBody));
    }

    @RolesAllowed({Privilege.ADMIN_USER_OPERATION})
    @GetMapping("/user")
    public ResponseEntity<Page<UserDto>> getUsers(PageRequest pageRequest) {
        return ResponseEntity.ok(adminService.getUsers(pageRequest));
    }

    @RolesAllowed({Privilege.ADMIN_USER_OPERATION})
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(adminService.getUser(id));
    }
}
