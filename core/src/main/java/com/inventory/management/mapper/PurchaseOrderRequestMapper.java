package com.inventory.management.mapper;

import com.inventory.management.domain.PurchaseOrderRequest;
import com.inventory.management.vo.dto.PurchaseOrderRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseOrderRequestMapper {

    PurchaseOrderRequest toEntity(PurchaseOrderRequestDto purchaseOrderRequestDto);
    PurchaseOrderRequestDto toDto(PurchaseOrderRequest purchaseOrderRequest);
}
