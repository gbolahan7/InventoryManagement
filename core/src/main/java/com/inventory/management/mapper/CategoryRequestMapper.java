package com.inventory.management.mapper;

import com.inventory.management.domain.CategoryRequest;
import com.inventory.management.vo.dto.CategoryRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryRequestMapper {

    CategoryRequest toEntity(CategoryRequestDto categoryRequestDto);
    CategoryRequestDto toDto(CategoryRequest categoryRequest);
}
