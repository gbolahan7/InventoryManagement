package com.inventory.management.service;

import com.inventory.management.vo.dto.TestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TestService {

    Page<TestDto> findAll(Pageable pageable);
}
