package com.inventory.management.mapper;

import com.inventory.management.domain.Product;
import com.inventory.management.util.audit.DefaultAudit;
import com.inventory.management.vo.dto.ProductAuditDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface ProductAuditMapper {
    ProductAuditDto toDto(DefaultAudit<Product> productAudit);
}