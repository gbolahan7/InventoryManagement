package com.inventory.management.mapper;

import com.inventory.management.domain.EntityLog;
import com.inventory.management.vo.dto.BaseAuditDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BaseAuditMapper {

    BaseAuditDto toDto(EntityLog entityLog);
}
