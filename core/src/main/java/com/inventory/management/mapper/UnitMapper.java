package com.inventory.management.mapper;

import com.inventory.management.domain.Unit;
import com.inventory.management.vo.dto.UnitDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UnitMapper {

    Unit toUnit(UnitDto unitDto);

    UnitDto toDto(Unit unit);
}
