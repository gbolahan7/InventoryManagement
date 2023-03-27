package com.inventory.management.mapper;

import com.inventory.management.domain.UnitRequest;
import com.inventory.management.vo.dto.UnitRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UnitRequestMapper {

    UnitRequest toEntity(UnitRequestDto unitRequestDto);
    UnitRequestDto toDto(UnitRequest unitRequest);
}