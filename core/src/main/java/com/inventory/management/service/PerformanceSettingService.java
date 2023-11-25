package com.inventory.management.service;

import com.inventory.management.vo.dto.PerformanceSettingAuditDto;
import com.inventory.management.vo.dto.PerformanceSettingDto;
import com.inventory.management.vo.dto.PerformanceSettingRequestDto;
import com.inventory.management.vo.request.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

public interface PerformanceSettingService {

    PerformanceSettingDto getPerformanceSetting(Long id);
    PerformanceSettingAuditDto getPerformanceSettingAudit(Long id, Integer revisionId);
    Page<PerformanceSettingAuditDto> getPerformanceSettingAudits(Long id, PageRequest pageRequest, Map<String, Object> performanceSettingAuditsFilter);
    Page<PerformanceSettingRequestDto> getPerformanceSettingRequests(PageRequest pageRequest, Map<String, Object> currencyRequestFilter);
    PerformanceSettingRequestDto getPerformanceSettingRequest(@PathVariable Long requestId);
    Long postPerformanceSettingRequest(PerformanceSettingRequestDto performanceSettingRequestBody);
    PerformanceSettingRequestDto approvePerformanceSettingRequest(Long requestId);
    PerformanceSettingRequestDto rejectPerformanceSettingRequest(Long requestId);

}
