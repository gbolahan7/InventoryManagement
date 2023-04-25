package com.inventory.management.operation.core.category.approve;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.Category;
import com.inventory.management.domain.CategoryRequest;
import com.inventory.management.operation.access.AccessOperation;
import com.inventory.management.repository.CategoryRepository;
import com.inventory.management.repository.CategoryRequestRepository;
import com.inventory.management.validation.CategoryRequestValidator;
import com.inventory.management.vo.problem.ValidationBuilder;
import com.inventory.management.vo.problem.ValidationException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static com.inventory.management.util.Constant.*;

@Operation
public class ApproveCategoryRequestOperation extends AccessOperation<CategoryRequest, Long, CategoryRequestRepository> {

    private final CategoryRepository categoryRepository;
    private final CategoryRequestValidator categoryRequestValidator;
    private final Map<String, Function<CategoryRequest, Category>> handlers = getApproveHandlers();

    public ApproveCategoryRequestOperation(CategoryRequestRepository categoryRequestRepository,
                                           CategoryRepository categoryRepository, CategoryRequestValidator categoryRequestValidator) {
        super(categoryRequestRepository);
        this.categoryRepository = categoryRepository;
        this.categoryRequestValidator = categoryRequestValidator;
    }

    @Override
    protected CategoryRequest operate(CategoryRequest requestEntity) {
        Category categoryEntity = handlers.get(requestEntity.getRequestType()).apply(requestEntity);
        categoryRepository.save(categoryEntity);
        requestEntity.setRequestStatus(APPROVED);
        return repository.save(requestEntity);
    }

    private Category handleCreate(CategoryRequest request) {
        categoryRequestValidator.validate(CREATE, request);
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setStatus(request.getStatus());
        category.setItems(new HashSet<>(request.getItems()));
        category.setVersion(UUID.randomUUID().toString());
        return category;
    }

    private Category handleModify(CategoryRequest request) {
        categoryRequestValidator.validate(MODIFY, request);
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ValidationException(new ValidationBuilder().addError("validation.error.entity.does.not.exist").build()));
        category.setDescription(request.getDescription());
        category.setStatus(request.getStatus());
        category.setItems(new HashSet<>(request.getItems()));
        category.setVersion(UUID.randomUUID().toString());
        return category;
    }

    private Map<String, Function<CategoryRequest, Category>> getApproveHandlers() {
        Map<String, Function<CategoryRequest, Category>> approveHandlers = new HashMap<>();
        approveHandlers.put(CREATE, this::handleCreate);
        approveHandlers.put(MODIFY, this::handleModify);
        return approveHandlers;
    }
}
