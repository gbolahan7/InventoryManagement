package com.inventory.management.service.impl;

import com.inventory.management.repository.TestRepository;
import com.inventory.management.service.TestService;
import com.inventory.management.vo.dto.TestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    @Override
    public Page<TestDto> findAll(Pageable pageable) {
        return testRepository.findAll(pageable).map(e -> new TestDto(e.getName()));
    }
}
