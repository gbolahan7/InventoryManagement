package com.inventory.management.operation.core.category.list.query;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.inventory.management.util.Constant.ADD;
import static com.inventory.management.util.Constant.MOD;
import static com.inventory.management.util.PageRequestHelper.createStringCriterion;
import static java.util.Optional.ofNullable;
import static org.hibernate.envers.query.AuditEntity.property;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryAuditQueryMapper {

    public static List<AuditCriterion> getAuditCriterionList(Map<String, Object> entityParams) {
        CategoryAuditQuery query = new CategoryAuditQuery();
        try {
            BeanUtils.populate(query, entityParams);
            List<AuditCriterion> auditCriterionList = new ArrayList<>();

            ofNullable(query.getRevisionType()).filter(e -> Arrays.asList(ADD, MOD).contains(e))
                    .ifPresent(e -> auditCriterionList.add(AuditEntity.revisionType().eq(RevisionType.valueOf(e))));
            ofNullable(query.getModifiedBy()).ifPresent(e -> auditCriterionList.add(createStringCriterion(e, "modifiedBy")));
            ofNullable(query.getModifiedDateFrom()).ifPresent(e -> auditCriterionList.add(property("modifiedDate").ge(Instant.parse(e))));
            ofNullable(query.getModifiedDateTo()).ifPresent(e -> auditCriterionList.add(property("modifiedDate").le(Instant.parse(e))));
            return auditCriterionList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
