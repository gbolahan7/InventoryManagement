package com.inventory.management.util;

import com.inventory.management.util.audit.DefaultAudit;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static org.hibernate.envers.query.AuditEntity.property;
import static org.hibernate.envers.query.criteria.MatchMode.ANYWHERE;
import static org.hibernate.envers.query.criteria.MatchMode.EXACT;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@RequiredArgsConstructor
@Service
public class PageRequestHelper {
    @Value("${app.page.max:10}")
    private Integer maxPageSize;

    static public <T> Page<T> removeSecondarySortingField(Page<T> pageResponse, String defaultSort) {
        Sort.Order order = pageResponse.getSort()
                .toList()
                .stream()
                .findFirst()
                .orElse(Sort.Order.asc(defaultSort));
        PageRequest request = PageRequest.of(pageResponse.getPageable().getPageNumber(), pageResponse.getPageable().getPageSize(),
                order.getDirection(), order.getProperty());
        return new PageImpl<>(pageResponse.getContent(), request, pageResponse.getTotalElements());
    }

    public static void trim(Map<String, Object> entityParams) {
        if (entityParams != null)
            entityParams.entrySet()
                    .stream()
                    .filter(p -> p.getValue() != null)
                    .filter(p -> p.getValue() instanceof String)
                    .forEach(p -> p.setValue(p.getValue().toString().trim()));
    }

    public static AuditCriterion createStringCriterion(String queryParameter, String propertyName) {
        return queryParameter.length() > 0 && queryParameter.endsWith("*") ?
                property(propertyName).ilike(queryParameter.substring(0, queryParameter.length() - 1), ANYWHERE) : property(propertyName).ilike(queryParameter, EXACT);
    }

    public PageRequest map(Serializable pageRequest, Class<?> entityClass, String defaultSortBy) {
        String sortBy = (String) invokeMethod(pageRequest, "getSortBy");
        String sortProperty = isSortPropertyExist(sortBy, entityClass) ?
                sortBy : defaultSortBy;
        return getPageRequest(pageRequest, sortProperty, getEntityIdField(entityClass));
    }

    public PageRequest mapForAudits(Serializable pageRequest, Class<?> entityClass, String defaultSortBy) {
        String sortBy = (String) invokeMethod(pageRequest, "getSortBy");
        String sortProperty = isSortPropertyExist(sortBy, entityClass) || isSortPropertyExist(sortBy, DefaultAudit.class) ?
                sortBy : defaultSortBy;
        return getPageRequest(pageRequest, sortProperty, "revisionId");
    }

    private boolean isSortPropertyExist(String field, Class<?> entityClass) {
        if (Objects.isNull(field) || field.isBlank())
            return false;
        return Stream.of(FieldUtils.getAllFields(entityClass))
                .filter(f -> {
                    if (entityClass.isAssignableFrom(DefaultAudit.class))
                        return !f.getName().equals("entity");
                    return f.isAnnotationPresent(Column.class);
                })
                .anyMatch(f -> f.getName().equals(field));
    }

    private String getEntityIdField(Class<?> entityClass) {
        Field[] fieldsWithAnnotation = FieldUtils.getFieldsWithAnnotation(entityClass, Id.class);
        return fieldsWithAnnotation[0].getName();
    }

    private PageRequest getPageRequest(Serializable pageRequest, String... sortProperties) {
        int page = (int) invokeMethod(pageRequest, "getPage");
        int size = (int) invokeMethod(pageRequest, "getSize");
        return PageRequest.of(page,
                Math.min(size, maxPageSize),
                getSort(pageRequest, sortProperties));
    }

    private Sort getSort(Serializable pageRequest, String... sortProperties) {
        return Sort.by(getDirection(pageRequest), sortProperties);
    }

    private Sort.Direction getDirection(Serializable pageRequest) {
        String sortDirection = (String) invokeMethod(pageRequest, "getSortDirection");
        return sortDirection.equalsIgnoreCase("desc") ? DESC : ASC;
    }

    private Object invokeMethod(Serializable pageRequest, String methodName) {
        try {
            Method getSortBy = pageRequest.getClass().getMethod(methodName);
            return getSortBy.invoke(pageRequest);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
}
