package com.inventory.management.mapper;

import com.inventory.management.domain.Unit;
import com.inventory.management.util.audit.DefaultAudit;
import com.inventory.management.vo.dto.UnitAuditDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UnitMapper.class)
public interface UnitAuditMapper {
    UnitAuditDto toDto(DefaultAudit<Unit> unitAudit);
}
