package com.inventory.management.mapper;

import com.inventory.management.domain.StaffPerformance;
import com.inventory.management.domain.StaffPerformance;
import com.inventory.management.vo.dto.StaffPerformanceDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StaffPerformanceMapper {

    StaffPerformance toStaffPerformance(StaffPerformanceDto staffPerformanceDto);

    StaffPerformanceDto toDto(StaffPerformance staffPerformance);
}
