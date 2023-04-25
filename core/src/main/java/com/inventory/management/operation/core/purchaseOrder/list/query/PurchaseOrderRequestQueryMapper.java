package com.inventory.management.operation.core.purchaseOrder.list.query;

import com.inventory.management.domain.QPurchaseOrderRequest;
import com.inventory.management.operation.core.purchaseOrder.list.query.PurchaseOrderRequestQuery;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;

import java.time.Instant;
import java.util.Map;

import static java.util.Optional.ofNullable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PurchaseOrderRequestQueryMapper {

    private static final QPurchaseOrderRequest QUERY = QPurchaseOrderRequest.purchaseOrderRequest;

    public static Predicate toPredicate(Map<String, Object> entityParams) {
        PurchaseOrderRequestQuery query = new PurchaseOrderRequestQuery();
        try {
            BeanUtils.populate(query, entityParams);
            BooleanBuilder where = new BooleanBuilder();

            ofNullable(query.getRequestStatus()).ifPresent(p -> where.and(QUERY.requestStatus.eq(p)));
            ofNullable(query.getRequestType()).ifPresent(p -> where.and(QUERY.requestType.eq(p)));
            ofNullable(query.getDescription()).ifPresent(p -> where.and(QUERY.description.likeIgnoreCase(p)));
            ofNullable(query.getCreatedDateFrom()).ifPresent(p -> where.and(QUERY.createdDate.goe(Instant.parse(p))));
            ofNullable(query.getCreatedDateTo()).ifPresent(p -> where.and(QUERY.createdDate.loe(Instant.parse(p))));
            return where;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
