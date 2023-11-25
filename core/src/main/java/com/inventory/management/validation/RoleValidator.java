package com.inventory.management.validation;

import com.inventory.management.annotation.Validate;
import com.inventory.management.domain.Role;
import com.inventory.management.repository.RoleRepository;
import com.inventory.management.util.Constant;
import com.inventory.management.util.StartupHelper;
import com.inventory.management.vo.dto.RoleDto;
import com.inventory.management.vo.problem.ValidationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Validate
@RequiredArgsConstructor
public class RoleValidator implements Validator<String, RoleDto> {

    private final static String VALID_ROLE_NAME = "^[A-Z][A-Z_]*$";
    private final RoleRepository roleRepository;
    private final Map<String, Consumer<RoleDto>> validators = registerValidators();
    private final static Set<String> AVAILABLE_PERMISSIONS = StartupHelper.getAllPrivileges();

    @Override
    public Map<String, Consumer<RoleDto>> getOperationValidators() {
        return validators;
    }

    private Map<String, Consumer<RoleDto>> registerValidators() {
        Map<String, Consumer<RoleDto>> validators = new HashMap<>();
        validators.put(Constant.CREATE, getCreationValidation());
        validators.put(Constant.MODIFY, getModifyValidation());
        return Map.copyOf(validators);
    }

    private Consumer<RoleDto> getCreationValidation() {
        return (request) -> {
            ValidationBuilder builder = new ValidationBuilder();
            buildBasicValidation(request, builder);
            if (builder.isClean()) {
                if (roleRepository.findByName(request.getName()).isPresent())
                    builder.addError("validation.error.role.name");
                if(!request.getName().matches(VALID_ROLE_NAME)) {
                    builder.addError("validation.error.role.name.not.valid");
                }
            }
            handleValidatorError(builder.build());
        };
    }

    private Consumer<RoleDto> getModifyValidation() {
        return (request) -> {
            ValidationBuilder builder = new ValidationBuilder();
            buildBasicValidation(request, builder);
            if(Objects.isNull(request.getId()) ) {
                builder.addError("validation.error.id.null");
            }
            if (builder.isClean()) {
                Optional<Role> optionalRole = roleRepository.findById(request.getId());
                if (optionalRole.isPresent()){
                    Role role = optionalRole.get();
                    if(!role.getName().equals(request.getName())) {
                        builder.addError("validation.error.role.name.not.modify");
                    }
                    if(StartupHelper.SUPER_ADMIN.equalsIgnoreCase(request.getName()) ||
                            StartupHelper.GUEST.equalsIgnoreCase(request.getName())) {
                        builder.addError("validation.error.role.name.not.modify.super-user.guest");
                    }
                }

            }
            handleValidatorError(builder.build());
        };
    }

    private static void buildBasicValidation(RoleDto request, ValidationBuilder builder) {
        if (Objects.isNull(request))
            builder.addError("validation.error.role.empty");
        else {
            if (!StringUtils.hasText(request.getName()))
                builder.addError("validation.error.role.name.empty");
            if (!StringUtils.hasText(request.getDescription()))
                builder.addError("validation.error.role.description.empty");
            if(!CollectionUtils.isEmpty(request.getPermissions())) {
                String unknownPermissions = request.getPermissions().stream()
                        .filter(permission -> !AVAILABLE_PERMISSIONS.contains(permission))
                        .collect(Collectors.joining(","));
                if(StringUtils.hasText(unknownPermissions))
                    builder.addError("validation.error.role.permission.unknown", unknownPermissions);
            }
        }
    }

}
