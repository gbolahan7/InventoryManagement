package com.inventory.management.operation.core.performanceSetting.list.query;

import com.inventory.management.domain.QPerformanceSettingRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;

import java.time.Instant;
import java.util.Map;

import static java.util.Optional.ofNullable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PerformanceSettingRequestQueryMapper {

    private static final QPerformanceSettingRequest QUERY = QPerformanceSettingRequest.performanceSettingRequest;

    public static Predicate toPredicate(Map<String, Object> entityParams) {
        PerformanceSettingRequestQuery query = new PerformanceSettingRequestQuery();
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
