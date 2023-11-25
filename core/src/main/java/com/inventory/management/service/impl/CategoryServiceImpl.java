package com.inventory.management.service.impl;

import com.inventory.management.domain.Category;
import com.inventory.management.domain.CategoryRequest;
import com.inventory.management.domain.Product;
import com.inventory.management.domain.ProductRequest;
import com.inventory.management.mapper.CategoryAuditMapper;
import com.inventory.management.mapper.CategoryMapper;
import com.inventory.management.mapper.CategoryRequestMapper;
import com.inventory.management.operation.access.AccessOperationRequest;
import com.inventory.management.operation.audit.ViewAuditOperationRequest;
import com.inventory.management.operation.core.category.approve.ApproveCategoryRequestOperation;
import com.inventory.management.operation.core.category.create.CreateCategoryOperation;
import com.inventory.management.operation.core.category.delete.DeleteCategoryOperation;
import com.inventory.management.operation.core.category.list.ListCategoryAuditOperation;
import com.inventory.management.operation.core.category.list.ListCategoryOperation;
import com.inventory.management.operation.core.category.list.ListCategoryRequestOperation;
import com.inventory.management.operation.core.category.reject.RejectCategoryRequestOperation;
import com.inventory.management.operation.core.category.view.ViewCategoryAuditOperation;
import com.inventory.management.operation.core.category.view.ViewCategoryOperation;
import com.inventory.management.operation.core.category.view.ViewCategoryRequestOperation;
import com.inventory.management.operation.create.CreateOperationRequest;
import com.inventory.management.operation.create.CreateOperationResponse;
import com.inventory.management.operation.delete.DeleteOperationRequest;
import com.inventory.management.operation.delete.DeleteOperationResponse;
import com.inventory.management.operation.list.ListAuditOperationRequest;
import com.inventory.management.operation.list.ListOperationRequest;
import com.inventory.management.operation.view.ViewOperationRequest;
import com.inventory.management.service.CategoryService;
import com.inventory.management.vo.dto.CategoryAuditDto;
import com.inventory.management.vo.dto.CategoryDto;
import com.inventory.management.vo.dto.CategoryRequestDto;
import com.inventory.management.vo.dto.ProductDto;
import com.inventory.management.vo.problem.CustomApiException;
import com.inventory.management.vo.problem.ValidationBuilder;
import com.inventory.management.vo.problem.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import com.inventory.management.vo.request.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final ViewCategoryOperation viewCategoryOperation;
    private final CategoryMapper categoryMapper;
    private final ListCategoryOperation listCategoryOperation;
    private final ListCategoryAuditOperation listCategoryAuditsOperation;
    private final ViewCategoryAuditOperation viewCategoryAuditOperation;
    private final CategoryAuditMapper categoryAuditResponseMapper;
    private final CategoryRequestMapper categoryRequestMapper;
    private final ViewCategoryRequestOperation viewCategoryRequestOperation;
    private final ListCategoryRequestOperation listCategoryRequestsOperation;
    private final CreateCategoryOperation createCategoryOperation;
    private final DeleteCategoryOperation deleteCategoryOperation;
    private final ApproveCategoryRequestOperation approveCategoryRequestOperation;
    private final RejectCategoryRequestOperation rejectCategoryRequestOperation;

    @Override
    public Page<CategoryDto> getCategories(PageRequest pageRequest, Map<String, Object> categoryFilter) {
        return listCategoryOperation.process(new ListOperationRequest(pageRequest, categoryFilter))
                .getPage()
                .map(categoryMapper::toDto);
    }

    @Override
    public CategoryDto getCategory(Long id) {
        return viewCategoryOperation.process(new ViewOperationRequest<>(id))
                .getEntity().map(categoryMapper::toDto)
                .orElseThrow(() -> new ValidationException( new ValidationBuilder().addError("validation.error.entity.does.not.exist").build()));
    }

    @Override
    public CategoryDto deleteCategory(Long id) {
        Category category = viewCategoryOperation.process(new ViewOperationRequest<>(id)).getEntity()
                .orElseThrow(() -> new ValidationException( new ValidationBuilder().addError("validation.error.entity.does.not.exist").build()));
        CategoryRequest categoryRequest = new CategoryRequest();
        BeanUtils.copyProperties(category, categoryRequest);
        categoryRequest.setCategoryId(category.getId());
        categoryRequest.setItems(new HashSet<>());
        DeleteOperationRequest<Category, CategoryRequest> request = new DeleteOperationRequest<>(category, categoryRequest);
        DeleteOperationResponse<Category> operationResponse = deleteCategoryOperation.process(request);
        return categoryMapper.toDto(operationResponse.getDomain());
    }

    @Override
    public CategoryAuditDto getCategoryAudit(Long id, Integer revisionId) {
        return viewCategoryAuditOperation.process(new ViewAuditOperationRequest<>(id, revisionId))
                .getAuditedEntity()
                .map(categoryAuditResponseMapper::toDto)
                .orElseThrow(() -> new CustomApiException("error has occurred"));
    }

    @Override
    public Page<CategoryAuditDto> getCategoryAudits(Long id, PageRequest pageRequest, Map<String, Object> categoryAuditsFilter) {
        return listCategoryAuditsOperation.process(new ListAuditOperationRequest<>(pageRequest, categoryAuditsFilter, id))
                .getPage().map(categoryAuditResponseMapper::toDto);
    }

    @Override
    public Page<CategoryRequestDto> getCategoryRequests(PageRequest pageRequest, Map<String, Object> currencyRequestFilter) {
        return listCategoryRequestsOperation
                .process(new ListOperationRequest(pageRequest, currencyRequestFilter))
                .getPage().map(categoryRequestMapper::toDto);
    }

    @Override
    public CategoryRequestDto getCategoryRequest(Long requestId) {
        return viewCategoryRequestOperation.process(new ViewOperationRequest<>(requestId))
                .getEntity().map(categoryRequestMapper::toDto).orElseThrow(() -> new CustomApiException("not found"));
    }

    @Override
    public Long postCategoryRequest(CategoryRequestDto categoryRequestBody) {
        CreateOperationRequest<CategoryRequest> request = new CreateOperationRequest<>(categoryRequestMapper.toEntity(categoryRequestBody));
        CreateOperationResponse<CategoryRequest> operationResponse = createCategoryOperation.process(request);
        return operationResponse.getRequest().getRequestId();
    }

    @Override
    public CategoryRequestDto approveCategoryRequest(Long requestId) {
        return categoryRequestMapper.toDto(approveCategoryRequestOperation.process(new AccessOperationRequest<>(requestId)).getEntity());
    }

    @Override
    public CategoryRequestDto rejectCategoryRequest(Long requestId) {
        return categoryRequestMapper.toDto(rejectCategoryRequestOperation.process(new AccessOperationRequest<>(requestId)).getEntity());
    }


}
