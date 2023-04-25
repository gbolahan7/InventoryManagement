package com.inventory.management.mapper;

import com.inventory.management.domain.PerformanceSettingRequest;
import com.inventory.management.vo.dto.PerformanceSettingRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PerformanceSettingRequestMapper {

    PerformanceSettingRequest toEntity(PerformanceSettingRequestDto performanceSettingRequestDto);
    PerformanceSettingRequestDto toDto(PerformanceSettingRequest performanceSettingRequest);
}
