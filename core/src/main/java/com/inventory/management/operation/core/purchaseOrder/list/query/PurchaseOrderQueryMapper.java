package com.inventory.management.operation.core.purchaseOrder.list.query;

import com.inventory.management.domain.QPurchaseOrder;
import com.inventory.management.util.PageRequestHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

import static java.util.Optional.ofNullable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PurchaseOrderQueryMapper {

    private static final QPurchaseOrder QUERY = QPurchaseOrder.purchaseOrder;

    public static Predicate toPredicate(Map<String, Object> params) {
        PurchaseOrderQuery query = new PurchaseOrderQuery();
        PageRequestHelper.trim(params);
        try {
            BeanUtils.populate(query, params);
            BooleanBuilder where = new BooleanBuilder();

            ofNullable(query.getId()).ifPresent(p -> where.and(QUERY.id.eq(p)));
            ofNullable(query.getDescription()).ifPresent(p -> where.and(QUERY.description.likeIgnoreCase(p)));

            return where;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
