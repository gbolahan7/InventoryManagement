package com.inventory.management.validation;

import com.inventory.management.annotation.Validate;
import com.inventory.management.domain.CategoryRequest;
import com.inventory.management.repository.CategoryRepository;
import com.inventory.management.util.Constant;
import com.inventory.management.vo.problem.ValidationBuilder;
import com.inventory.management.vo.problem.ValidationException;
import com.inventory.management.vo.problem.ValidatorError;
import lombok.AllArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

@Validate
@AllArgsConstructor
public class CategoryRequestValidator implements Validator<String, CategoryRequest> {

    final private CategoryRepository categoryRepository;

    private final Map<String, Consumer<CategoryRequest>> validators = registerValidators();

    private static void buildBasicValidation(CategoryRequest request, ValidationBuilder builder) {
        if (Objects.isNull(request))
            builder.addError("validation.error.category.empty");
        else {
            if (!StringUtils.hasText(request.getName()))
                builder.addError("validation.error.category.name.empty");
            if (!StringUtils.hasText(request.getStatus()))
                builder.addError("validation.error.category.status.empty");
            else if (!request.getStatus().equals("Active") && !request.getStatus().equals("Inactive")) {
                builder.addError("validation.error.category.status.allowed");
            }
        }
    }

    @Override
    public Map<String, Consumer<CategoryRequest>> getOperationValidators() {
        return validators;
    }

    private Map<String, Consumer<CategoryRequest>> registerValidators() {
        Map<String, Consumer<CategoryRequest>> validators = new HashMap<>();
        validators.put(Constant.CREATE, getCreationValidation());
        validators.put(Constant.MODIFY, getModifyValidation());
        return Map.copyOf(validators);
    }

    private Consumer<CategoryRequest> getCreationValidation() {
        return (request) -> {
            ValidationBuilder builder = new ValidationBuilder();
            buildBasicValidation(request, builder);
            if (builder.isClean()) {
                if (categoryRepository.findByName(request.getName()).isPresent())
                    builder.addError("validation.error.category.name");
            }
            handleValidatorError(builder.build());
        };
    }

    private Consumer<CategoryRequest> getModifyValidation() {
        return (request) -> {
            ValidationBuilder builder = new ValidationBuilder();
            buildBasicValidation(request, builder);
            if (builder.isClean()) {
                if (categoryRepository.findById(request.getCategoryId()).isEmpty())
                    builder.addError("validation.error.category.request");
            }
            handleValidatorError(builder.build());
        };
    }

}
