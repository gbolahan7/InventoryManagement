package com.inventory.management.vo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
public class PurchaseOrderDto extends BaseAuditDto {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());
    private Long id;
    private Instant purchasedDate;
    private String description;
    private String status;
    private List<PurchaseOrderItemDto> items;

    @JsonIgnore
    public String getFormattedPurchasedDate() {
        if(Objects.isNull(purchasedDate)) return null;
        return formatter.format(purchasedDate);
    }

    @JsonIgnore
    public String getFormattedItems() {
        if(CollectionUtils.isEmpty(items)) return null;
        return items.stream()
                .map(item -> String.format("Product: %s - Quantity: %s", item.getProductName(), item.getQuantity()))
                .collect(Collectors.joining("\n"));
    }
}
