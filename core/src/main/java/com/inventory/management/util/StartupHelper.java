package com.inventory.management.util;

import com.inventory.management.auth.Privilege;
import com.inventory.management.domain.PerformanceSetting;
import com.inventory.management.domain.Role;
import com.inventory.management.repository.PerformanceSettingRepository;
import com.inventory.management.repository.RoleRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Transactional(propagation = Propagation.REQUIRES_NEW)
public class StartupHelper {
    public static final String SUPER_ADMIN = "SUPER_ADMIN";
    public static final String GUEST = "GUEST";
    public static final String PERFORMANCE_SETTING_NAME = "defaultPerformanceSetting";

    public StartupHelper(RoleRepository roleRepository, PerformanceSettingRepository performanceSettingRepository) {
        startupRoleSuperUser(roleRepository);
        startupRoleGuest(roleRepository);
        ensurePerformanceSetting(performanceSettingRepository);
    }


    private void startupRoleSuperUser(RoleRepository roleRepository) {
        Role value = roleRepository.findByName(SUPER_ADMIN).orElseGet(() -> {
            Role role = new Role();
            role.setDescription("A role representing the topmost hierarchical role for other roles");
            role.setName(SUPER_ADMIN);
            return role;
        });
        value.setPrivileges(getAllPrivileges());
        roleRepository.save(value);
    }

    private void startupRoleGuest(RoleRepository roleRepository) {
        roleRepository.findByName(GUEST).orElseGet(() -> {
            Role role = new Role();
            role.setDescription("A role without any privilege");
            role.setName(GUEST);
            role.setPrivileges(Set.of());
            return roleRepository.save(role);
        });
    }

    private void ensurePerformanceSetting(PerformanceSettingRepository performanceSettingRepository) {
        Optional<PerformanceSetting> optionalPerformanceSetting = performanceSettingRepository.findByName(PERFORMANCE_SETTING_NAME);
        if(optionalPerformanceSetting.isEmpty()) {
            PerformanceSetting setting = new PerformanceSetting();
            setting.setName(PERFORMANCE_SETTING_NAME);
            setting.setVersion(UUID.randomUUID().toString());
            performanceSettingRepository.save(setting);
        }
    }

    public static Set<String> getAllPrivileges() {
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
