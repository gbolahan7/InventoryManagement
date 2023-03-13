package com.inventory.management.validation;

import com.inventory.management.annotation.Validate;
import com.inventory.management.domain.Constants;
import com.inventory.management.domain.Role;
import com.inventory.management.domain.User;
import com.inventory.management.repository.RoleRepository;
import com.inventory.management.repository.UserRepository;
import com.inventory.management.util.Constant;
import com.inventory.management.util.StartupHelper;
import com.inventory.management.vo.dto.RoleDto;
import com.inventory.management.vo.dto.UserDto;
import com.inventory.management.vo.problem.ValidationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Validate
@RequiredArgsConstructor
public class UserValidator implements Validator<String, UserDto> {

    private final UserRepository userRepository;
    private final Map<String, Consumer<UserDto>> validators = registerValidators();

    @Override
    public Map<String, Consumer<UserDto>> getOperationValidators() {
        return validators;
    }

    private Map<String, Consumer<UserDto>> registerValidators() {
        Map<String, Consumer<UserDto>> validators = new HashMap<>();
        validators.put(Constant.MODIFY, getModifyValidation());
        return Map.copyOf(validators);
    }
    

    private Consumer<UserDto> getModifyValidation() {
        return (request) -> {
            ValidationBuilder builder = new ValidationBuilder();
            buildBasicValidation(request, builder);
            if (builder.isClean()) {
                Optional<User> optionalUser = userRepository.findById(request.getId());
                optionalUser.ifPresentOrElse((user) -> buildModifyValidation(request, builder), () -> builder.addError("validation.error.user.not.exist"));
            }
            handleValidatorError(builder.build());
        };
    }

    private void buildBasicValidation(UserDto request, ValidationBuilder builder) {
        if (Objects.isNull(request))
            builder.addError("validation.error.user.empty");
        else {
            if (Objects.isNull(request.getId()))
                builder.addError("validation.error.user.id.empty");
            if (!StringUtils.hasText(request.getStatus()))
                builder.addError("validation.error.user.status.empty");
            if (CollectionUtils.isEmpty(request.getRoles()))
                builder.addError("validation.error.user.role.empty");
            else if(request.getRoles().size() > 1)
                builder.addError("validation.error.user.multiple.roles.not.supported");
            else if(this.extractRole(request) == null || this.extractRole(request).getId() == null)
                builder.addError("validation.error.user.role.id.empty");
        }
    }

    private void buildModifyValidation(UserDto request, ValidationBuilder builder) {
        List<User> users = userRepository.findAllUserWithSuperAdminRole();
        long count = users.size();
        if(count == 1) {
            RoleDto roleRequest = extractRole(request);
            Role role = users.get(0).getRoles().stream().findFirst().orElseThrow();
            if (users.get(0).getId().equals(request.getId()) && !role.getId().equals(roleRequest.getId()))
                builder.addError("validation.error.user.superuser.one");
            if (users.get(0).getId().equals(request.getId()) && request.getStatus().equals(Constants.Status.INACTIVE.getValue()))
                builder.addError("validation.error.user.superuser.one.disable.status");
        }
        try {
            Constants.Status.valueOf(request.getStatus());
        }catch (IllegalArgumentException ex) {
            builder.addError("validation.error.user.status.illegal");
        }
    }

    RoleDto extractRole(UserDto userDto) {
        return userDto.getRoles().stream().findFirst().orElse(null);
    }

}
