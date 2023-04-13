package com.inventory.management.mapper;

import com.inventory.management.domain.ProductRequest;
import com.inventory.management.vo.dto.ProductRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductRequestMapper {

    ProductRequest toEntity(ProductRequestDto productRequestDto);
    ProductRequestDto toDto(ProductRequest productRequest);
}