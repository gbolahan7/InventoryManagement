package com.inventory.management.mapper;

import com.inventory.management.domain.PurchaseOrder;
import com.inventory.management.util.audit.DefaultAudit;
import com.inventory.management.vo.dto.PurchaseOrderAuditDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = PurchaseOrderMapper.class)
public interface PurchaseOrderAuditMapper {
    PurchaseOrderAuditDto toDto(DefaultAudit<PurchaseOrder> purchaseOrderAudit);
}
