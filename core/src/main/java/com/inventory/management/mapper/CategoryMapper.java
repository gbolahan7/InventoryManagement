package com.inventory.management.mapper;

import com.inventory.management.domain.Category;
import com.inventory.management.vo.dto.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategory(CategoryDto categoryDto);

    CategoryDto toDto(Category category);
}
