package com.inventory.management.util;

import com.inventory.management.auth.Privilege;
import com.inventory.management.domain.Role;
import com.inventory.management.repository.RoleRepository;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class StartupHelper {
    public static final String SUPER_ADMIN = "SUPER_ADMIN";
    public static final String GUEST = "GUEST";

    public StartupHelper(RoleRepository roleRepository) {
        startupRoleSuperUser(roleRepository);
        startupRoleGuest(roleRepository);
    }


    private void startupRoleSuperUser(RoleRepository roleRepository) {
        roleRepository.findByName(SUPER_ADMIN).orElseGet(() -> {
            Role role = new Role();
            role.setName(SUPER_ADMIN);
            role.setPrivileges(getAllPrivileges());
            return roleRepository.save(role);
        });
    }

    private void startupRoleGuest(RoleRepository roleRepository) {
        roleRepository.findByName(GUEST).orElseGet(() -> {
            Role role = new Role();
            role.setName(GUEST);
            role.setPrivileges(Set.of());
            return roleRepository.save(role);
        });
    }

    private Set<String> getAllPrivileges() {
        Set<String> privileges = new HashSet<>();
        Class<Privilege> clazz = Privilege.class;
        Field[] arr = clazz.getFields();
        for (Field field : arr) {
            if (field.getType().equals(String.class)) {
                String s = (String) ReflectionUtils.getField(field, null);
                privileges.add(s);
            }
        }
        return privileges;
    }
}