//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.inventory.management.util.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.criteria.AuditProperty;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

@Service
public class JpaAuditService implements AuditService {
    private final EntityManager em;
    private final ObjectMapper objectMapper;
    private final Map<String, AuditProperty<?>> sortProperties;

    public JpaAuditService(EntityManager em, ObjectMapper objectMapper) {
        this.em = em;
        this.objectMapper = objectMapper;
        this.sortProperties = new HashMap();
        this.sortProperties.put("revisionId", AuditEntity.property("id"));
        this.sortProperties.put("revisionDate", AuditEntity.revisionProperty("timestamp"));
        this.sortProperties.put("revisionType", AuditEntity.revisionType());
    }

    public <T> DefaultAudit<T> getAuditOf(Class<T> entityClass, AuditCriterion criterion, Integer revisionId) {
        AuditQuery query = this.basicQuery(entityClass).add(criterion).add(AuditEntity.revisionNumber().eq(revisionId));
        return RevisionMapper.toRevisionAuditedEntity((T[])query.getSingleResult());
    }

    public <T> Page<DefaultAudit<T>> getAuditsOf(Class<T> entityClass, List<AuditCriterion> criterionList, PageRequest pageRequest, List<String> selectedFields) {
        AuditQuery auditCountQuery = AuditReaderFactory.get(this.em).createQuery().forRevisionsOfEntity(entityClass, false, true).addProjection(AuditEntity.id().count());
        Objects.requireNonNull(auditCountQuery);
        criterionList.forEach(auditCountQuery::add);
        long totalCount = (Long)auditCountQuery.getSingleResult();
        AuditQuery auditQuery = this.basicQuery(entityClass).addOrder(this.getAuditOrder(pageRequest.getSort())).setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize()).setMaxResults(pageRequest.getPageSize());
        Objects.requireNonNull(auditQuery);
        criterionList.forEach(auditQuery::add);
        if (this.isSelectionExists(selectedFields)) {
            auditQuery.addProjection(AuditEntity.revisionNumber());
            auditQuery.addProjection(AuditEntity.revisionProperty("timestamp"));
            auditQuery.addProjection(AuditEntity.revisionType());
            selectedFields.forEach((e) -> {
                auditQuery.addProjection(AuditEntity.property(e));
            });
        }

        List<Object[]> resultList = auditQuery.getResultList();
        List<Object[]> finalResult = this.isSelectionExists(selectedFields) ? (List)resultList.stream().map((auditFields) -> {
            return this.getAuditEntity(entityClass, selectedFields, auditFields);
        }).collect(Collectors.toList()) : resultList;
        List<DefaultAudit<T>> results = (List)finalResult.stream().map(RevisionMapper::toRevisionAuditedEntity).collect(Collectors.toList());
        return new PageImpl(results, pageRequest, totalCount);
    }

    private boolean isSelectionExists(List<String> selectedFields) {
        return Objects.nonNull(selectedFields) && !selectedFields.isEmpty();
    }

    private AuditOrder getAuditOrder(Sort sort) {
        Order order = (Order)sort.get().findFirst().orElseGet(() -> {
            return Order.desc("revisionId");
        });
        return order.isAscending() ? ((AuditProperty)this.sortProperties.getOrDefault(order.getProperty(), AuditEntity.property(order.getProperty()))).asc() : ((AuditProperty)this.sortProperties.getOrDefault(order.getProperty(), AuditEntity.property(order.getProperty()))).desc();
    }

    private AuditQuery basicQuery(Class<?> entityClass) {
        AuditReader reader = AuditReaderFactory.get(this.em);
        return reader.createQuery().forRevisionsOfEntity(entityClass, false, true);
    }

    private <T> Object[] getAuditEntity(Class<T> entityClass, List<String> selectedFields, Object[] auditFields) {
        Object[] auditEntity = new Object[]{this.getEntity(entityClass, selectedFields, auditFields), this.getDefaultRevisionEntity(auditFields), auditFields[2]};
        return auditEntity;
    }

    private DefaultRevisionEntity getDefaultRevisionEntity(Object[] auditFields) {
        DefaultRevisionEntity defaultRevisionEntity = new DefaultRevisionEntity();
        defaultRevisionEntity.setId((Integer)auditFields[0]);
        defaultRevisionEntity.setTimestamp((Long)auditFields[1]);
        return defaultRevisionEntity;
    }

    private <T> T getEntity(Class<T> entityClass, List<String> selectedFields, Object[] auditFields) {
        Map<String, Object> entityAsMap = new HashMap();

        for(int i = 3; i < auditFields.length; ++i) {
            entityAsMap.put((String)selectedFields.get(i - 3), auditFields[i]);
        }

        return this.objectMapper.convertValue(entityAsMap, entityClass);
    }
}
