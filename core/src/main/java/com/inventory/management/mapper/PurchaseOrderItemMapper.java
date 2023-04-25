package com.inventory.management.mapper;

import com.inventory.management.domain.PurchaseOrder;
import com.inventory.management.domain.PurchaseOrderItem;
import com.inventory.management.vo.dto.PurchaseOrderDto;
import com.inventory.management.vo.dto.PurchaseOrderItemDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseOrderItemMapper {

    PurchaseOrderItem toPurchaseOrderItem(PurchaseOrderItemDto purchaseOrderDto);

    PurchaseOrderItemDto toDto(PurchaseOrderItem purchaseOrder);
}
