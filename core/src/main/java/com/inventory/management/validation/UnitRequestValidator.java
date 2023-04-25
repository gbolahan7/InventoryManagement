package com.inventory.management.validation;

import com.inventory.management.annotation.Validate;
import com.inventory.management.domain.UnitRequest;
import com.inventory.management.repository.UnitRepository;
import com.inventory.management.util.Constant;
import com.inventory.management.vo.problem.ValidationBuilder;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

@Validate
@AllArgsConstructor
public class UnitRequestValidator implements Validator<String, UnitRequest> {

    final private UnitRepository unitRepository;

    private final Map<String, Consumer<UnitRequest>> validators = registerValidators();

    private static void buildBasicValidation(UnitRequest request, ValidationBuilder builder) {
        if (Objects.isNull(request))
            builder.addError("validation.error.unit.empty");
        else {
            if (!StringUtils.hasText(request.getName()))
                builder.addError("validation.error.unit.name.empty");
            if (!StringUtils.hasText(request.getStatus()))
                builder.addError("validation.error.unit.status.empty");
            else if (!request.getStatus().equals("Active") && !request.getStatus().equals("Inactive")) {
                builder.addError("validation.error.unit.status.allowed");
            }
        }
    }

    @Override
    public Map<String, Consumer<UnitRequest>> getOperationValidators() {
        return validators;
    }

    private Map<String, Consumer<UnitRequest>> registerValidators() {
        Map<String, Consumer<UnitRequest>> validators = new HashMap<>();
        validators.put(Constant.CREATE, getCreationValidation());
        validators.put(Constant.MODIFY, getModifyValidation());
        return Map.copyOf(validators);
    }

    private Consumer<UnitRequest> getCreationValidation() {
        return (request) -> {
            ValidationBuilder builder = new ValidationBuilder();
            buildBasicValidation(request, builder);
            if (builder.isClean()) {
                if (unitRepository.findByName(request.getName()).isPresent())
                    builder.addError("validation.error.unit.name");
            }
            handleValidatorError(builder.build());
        };
    }

    private Consumer<UnitRequest> getModifyValidation() {
        return (request) -> {
            ValidationBuilder builder = new ValidationBuilder();
            buildBasicValidation(request, builder);
            if (builder.isClean()) {
                if (unitRepository.findById(request.getUnitId()).isEmpty())
                    builder.addError("validation.error.unit.request");
            }
            handleValidatorError(builder.build());
        };
    }

}
