package com.inventory.management.controller;

import com.inventory.management.annotation.ResponseWrapper;
import com.inventory.management.auth.Privilege;
import com.inventory.management.service.CategoryService;
import com.inventory.management.vo.dto.CategoryAuditDto;
import com.inventory.management.vo.dto.CategoryDto;
import com.inventory.management.vo.dto.CategoryRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import com.inventory.management.vo.request.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("${api.base-path:/api/v1}"+"/category")
@ResponseWrapper
public class CategoryController {

    private final CategoryService categoryService;

    @RolesAllowed({Privilege.INVENTORY_CATEGORY_VIEW})
    @GetMapping
    public ResponseEntity<Page<CategoryDto>> getCategories(PageRequest pageRequest, @RequestParam(value = "", required = false)  Map<String, Object> categoryFilter) {
        return ResponseEntity.ok(categoryService.getCategories(pageRequest, categoryFilter));
    }

    @RolesAllowed({Privilege.INVENTORY_CATEGORY_VIEW})
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    @RolesAllowed({Privilege.INVENTORY_CATEGORY_VIEW})
    @GetMapping("/audit/id/{id}/revision/{revisionId}")
    public ResponseEntity<CategoryAuditDto> getCategoryAudit(@PathVariable Long id, @PathVariable Integer revisionId) {
        return ResponseEntity.ok(categoryService.getCategoryAudit(id, revisionId));
    }

    @RolesAllowed({Privilege.INVENTORY_CATEGORY_VIEW})
    @GetMapping("/audit/id/{id}")
    public ResponseEntity<Page<CategoryAuditDto>> getCategoryAudits(@PathVariable Long id, PageRequest pageRequest, @RequestParam(value = "", required = false)  Map<String, Object> categoryAuditsFilter) {
        return ResponseEntity.ok(categoryService.getCategoryAudits(id, pageRequest, categoryAuditsFilter));
    }

    @RolesAllowed({Privilege.INVENTORY_CATEGORY_VIEW})
    @GetMapping("/request")
    public ResponseEntity<Page<CategoryRequestDto>> getCategoryRequests( PageRequest pageRequest, @RequestParam(value = "", required = false)  Map<String, Object> categoryRequestFilter) {
        return ResponseEntity.ok(categoryService.getCategoryRequests(pageRequest, categoryRequestFilter));
    }

    @RolesAllowed({Privilege.INVENTORY_CATEGORY_VIEW})
    @GetMapping("/request/{requestId}")
    public ResponseEntity<CategoryRequestDto> getCategoryRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(categoryService.getCategoryRequest(requestId));
    }

    @RolesAllowed({Privilege.INVENTORY_CATEGORY_CREATE})
    @PostMapping("/request/create")
    public ResponseEntity<Long> createCategoryRequest(@RequestBody CategoryRequestDto categoryRequestBody) {
        categoryRequestBody.setRequestType("create");
        return postCategoryRequest(categoryRequestBody);
    }

    @RolesAllowed({Privilege.INVENTORY_CATEGORY_MODIFY})
    @PostMapping("/request/modify")
    public ResponseEntity<Long> modifyCategoryRequest(@RequestBody CategoryRequestDto categoryRequestBody) {
        categoryRequestBody.setRequestType("modify");
        return postCategoryRequest(categoryRequestBody);
    }

    @RolesAllowed({Privilege.INVENTORY_CATEGORY_ACCESS})
    @GetMapping("/request/approve/{requestId}")
    public ResponseEntity<CategoryRequestDto> approveCategoryRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(categoryService.approveCategoryRequest(requestId));
    }

    @RolesAllowed({Privilege.INVENTORY_CATEGORY_ACCESS})
    @GetMapping("/request/reject/{requestId}")
    public ResponseEntity<CategoryRequestDto> rejectCategoryRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(categoryService.rejectCategoryRequest(requestId));
    }

    private ResponseEntity<Long> postCategoryRequest(CategoryRequestDto categoryRequestBody) {
        long id = categoryService.postCategoryRequest(categoryRequestBody);
        return ResponseEntity.created(URI.create("/" + id)).body(id);
    }

}
