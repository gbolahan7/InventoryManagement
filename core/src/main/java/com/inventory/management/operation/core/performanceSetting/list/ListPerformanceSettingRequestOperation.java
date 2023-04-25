package com.inventory.management.operation.core.performanceSetting.list;

import com.inventory.management.annotation.Operation;
import com.inventory.management.domain.PerformanceSettingRequest;
import com.inventory.management.operation.core.performanceSetting.list.query.PerformanceSettingRequestQueryMapper;
import com.inventory.management.operation.list.ListOperation;
import com.inventory.management.repository.PerformanceSettingRequestRepository;
import com.inventory.management.util.PageRequestHelper;
import com.querydsl.core.types.Predicate;

import java.util.Map;

@Operation
public class ListPerformanceSettingRequestOperation extends ListOperation<PerformanceSettingRequest, Long, PerformanceSettingRequestRepository> {

    public ListPerformanceSettingRequestOperation(PerformanceSettingRequestRepository repository, PageRequestHelper pageRequestMapper) {
        super(repository, pageRequestMapper, PerformanceSettingRequest.class, "requestId");
    }

    @Override
    protected Predicate getPredicate(Map<String, Object> filter) {
        return PerformanceSettingRequestQueryMapper.toPredicate(filter);
    }
}
