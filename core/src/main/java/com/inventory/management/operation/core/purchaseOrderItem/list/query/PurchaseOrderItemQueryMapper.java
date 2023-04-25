package com.inventory.management.operation.core.purchaseOrderItem.list.query;

import com.inventory.management.domain.QPurchaseOrder;
import com.inventory.management.domain.QPurchaseOrderItem;
import com.inventory.management.util.PageRequestHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

import static java.util.Optional.ofNullable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PurchaseOrderItemQueryMapper {

    private static final QPurchaseOrderItem QUERY = QPurchaseOrderItem.purchaseOrderItem;

    public static Predicate toPredicate(Map<String, Object> params) {
        PurchaseOrderItemQuery query = new PurchaseOrderItemQuery();
        PageRequestHelper.trim(params);
        try {
            BeanUtils.populate(query, params);
            BooleanBuilder where = new BooleanBuilder();

            ofNullable(query.getId()).ifPresent(p -> where.and(QUERY.id.eq(p)));
            ofNullable(query.getPurchaseOrderId()).ifPresent(p -> where.and(QUERY.purchaseOrder.id.eq(p)));
            ofNullable(query.getProductCode()).ifPresent(p -> where.and(QUERY.productCode.likeIgnoreCase(p)));
            ofNullable(query.getProductName()).ifPresent(p -> where.and(QUERY.productName.likeIgnoreCase(p)));

            return where;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
