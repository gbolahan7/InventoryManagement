package com.inventory.management.mapper;

import com.inventory.management.domain.PerformanceSetting;
import com.inventory.management.vo.dto.PerformanceSettingDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PerformanceSettingMapper {

    PerformanceSetting toPerformanceSetting(PerformanceSettingDto performanceSettingDto);

    PerformanceSettingDto toDto(PerformanceSetting performanceSetting);
}
