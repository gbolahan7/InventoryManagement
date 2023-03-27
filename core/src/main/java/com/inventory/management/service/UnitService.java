package com.inventory.management.service;

import com.inventory.management.vo.dto.UnitAuditDto;
import com.inventory.management.vo.dto.UnitDto;
import com.inventory.management.vo.dto.UnitRequestDto;
import com.inventory.management.vo.request.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

public interface UnitService {

    Page<UnitDto> getUnits(PageRequest pageRequest, Map<String, Object> unitFilter);
    UnitDto getUnit(Long id);
    UnitAuditDto getUnitAudit(Long id, Integer revisionId);
    Page<UnitAuditDto> getUnitAudits(Long id, PageRequest pageRequest, Map<String, Object> unitAuditsFilter);
    Page<UnitRequestDto> getUnitRequests(PageRequest pageRequest, Map<String, Object> currencyRequestFilter);
    UnitRequestDto getUnitRequest(@PathVariable Long requestId);
    Long postUnitRequest(UnitRequestDto unitRequestBody);
    UnitRequestDto approveUnitRequest(Long requestId);
    UnitRequestDto rejectUnitRequest(Long requestId);

}