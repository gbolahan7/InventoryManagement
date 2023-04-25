package com.inventory.management.mapper;

import com.inventory.management.domain.PerformanceSetting;
import com.inventory.management.util.audit.DefaultAudit;
import com.inventory.management.vo.dto.PerformanceSettingAuditDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = PerformanceSettingMapper.class)
public interface PerformanceSettingAuditMapper {
    PerformanceSettingAuditDto toDto(DefaultAudit<PerformanceSetting> performanceSettingAudit);
}
