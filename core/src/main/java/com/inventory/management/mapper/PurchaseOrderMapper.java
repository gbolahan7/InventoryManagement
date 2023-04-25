package com.inventory.management.mapper;

import com.inventory.management.domain.PurchaseOrder;
import com.inventory.management.vo.dto.PurchaseOrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseOrderMapper {

    PurchaseOrder toPurchaseOrder(PurchaseOrderDto purchaseOrderDto);

    PurchaseOrderDto toDto(PurchaseOrder purchaseOrder);
}
