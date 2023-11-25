package com.inventory.management.operation.core.product.list.query;

import com.inventory.management.domain.QProductRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;

import java.time.Instant;
import java.util.Map;

import static java.util.Optional.ofNullable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductRequestQueryMapper {

    private static final QProductRequest QUERY = QProductRequest.productRequest;

    public static Predicate toPredicate(Map<String, Object> entityParams) {
        ProductRequestQuery query = new ProductRequestQuery();
        try {
            BeanUtils.populate(query, entityParams);
            BooleanBuilder where = new BooleanBuilder();

            ofNullable(query.getRequestStatus()).ifPresent(p -> where.and(QUERY.requestStatus.eq(p)));
            ofNullable(query.getRequestType()).ifPresent(p -> where.and(QUERY.requestType.eq(p)));
            ofNullable(query.getName()).ifPresent(p -> where.and(QUERY.name.eq(p)));
            ofNullable(query.getCreatedDateFrom()).ifPresent(p -> where.and(QUERY.createdDate.goe(Instant.parse(p))));
            ofNullable(query.getCreatedDateTo()).ifPresent(p -> where.and(QUERY.createdDate.loe(Instant.parse(p))));
            return where;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
