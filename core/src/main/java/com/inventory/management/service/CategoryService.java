package com.inventory.management.service;

import com.inventory.management.operation.list.ListOperationRequest;
import com.inventory.management.vo.dto.CategoryAuditDto;
import com.inventory.management.vo.dto.CategoryDto;
import com.inventory.management.vo.dto.CategoryRequestDto;
import com.inventory.management.vo.response.GenericResponse;
import org.springframework.data.domain.Page;
import com.inventory.management.vo.request.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

public interface CategoryService {

    Page<CategoryDto> getCategories(PageRequest pageRequest, Map<String, Object> categoryFilter);
    CategoryDto getCategory(Long id);
    CategoryAuditDto getCategoryAudit(Long id, Integer revisionId);
    Page<CategoryAuditDto> getCategoryAudits(Long id, PageRequest pageRequest, Map<String, Object> categoryAuditsFilter);
    Page<CategoryRequestDto> getCategoryRequests(PageRequest pageRequest, Map<String, Object> currencyRequestFilter);
    CategoryRequestDto getCategoryRequest(@PathVariable Long requestId);
    Long postCategoryRequest(CategoryRequestDto categoryRequestBody);
    CategoryRequestDto approveCategoryRequest(Long requestId);
    CategoryRequestDto rejectCategoryRequest(Long requestId);

}
