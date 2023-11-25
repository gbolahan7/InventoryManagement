package com.inventory.management.mapper;

import com.inventory.management.domain.Category;
import com.inventory.management.util.audit.DefaultAudit;
import com.inventory.management.vo.dto.CategoryAuditDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface CategoryAuditMapper {
    CategoryAuditDto toDto(DefaultAudit<Category> categoryAudit);
}
