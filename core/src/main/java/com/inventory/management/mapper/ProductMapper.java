package com.inventory.management.mapper;

import com.inventory.management.domain.Product;
import com.inventory.management.vo.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(ProductDto productDto);

    ProductDto toDto(Product product);
}
