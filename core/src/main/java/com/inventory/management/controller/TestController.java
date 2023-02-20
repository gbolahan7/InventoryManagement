package com.inventory.management.controller;

import com.inventory.management.annotation.ResponseWrapper;
import com.inventory.management.auth.Privilege;
import com.inventory.management.service.TestService;
import com.inventory.management.vo.dto.TestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.security.RolesAllowed;
@RestController
@RequestMapping("${api.base-path:/api/test/v1}")
@RequiredArgsConstructor
@ResponseWrapper
public class TestController {

    private final TestService testService;

    @GetMapping("/test-endpoint")
    @RolesAllowed(Privilege.INVENTORY_PRODUCT_ADD)
    public ResponseEntity<Page<TestDto>> getAllTest(@PageableDefault(value = 30) Pageable pageable) {
        return ResponseEntity.ok().body(testService.findAll(pageable));
    }

}
