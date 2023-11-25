package com.inventory.management.operation.core.category.list.query;

import com.inventory.management.domain.QCategory;
import com.inventory.management.util.PageRequestHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

import static java.util.Optional.ofNullable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryQueryMapper {

    private static final QCategory QUERY = QCategory.category;

    public static Predicate toPredicate(Map<String, Object> params) {
        CategoryQuery query = new CategoryQuery();
        PageRequestHelper.trim(params);
        try {
            BeanUtils.populate(query, params);
            BooleanBuilder where = new BooleanBuilder();

            ofNullable(query.getId()).ifPresent(p -> where.and(QUERY.id.eq(p)));
            ofNullable(query.getName()).ifPresent(p -> where.and(QUERY.name.eq(p)));
            ofNullable(query.getDescription()).ifPresent(p -> where.and(QUERY.description.likeIgnoreCase(p)));

            return where;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
