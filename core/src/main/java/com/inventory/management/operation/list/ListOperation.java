package com.inventory.management.operation.list;

import com.inventory.management.operation.Operation;
import com.inventory.management.repository.CustomQuerydslPredicateExecutor;
import com.inventory.management.util.PageRequestHelper;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.Map;

@AllArgsConstructor
public abstract class ListOperation<T, I, R extends CrudRepository<T, I> & CustomQuerydslPredicateExecutor<T>> implements Operation<ListOperationRequest, ListOperationResponse<T>> {
    private final R repository;
    private final PageRequestHelper pageRequestHelper;
    private final Class<T> entityClass;
    private final String defaultSortColumn;

    @Override
    public ListOperationResponse<T> process(ListOperationRequest request) {
        Predicate predicate = getPredicate(request.getFilter());
        PageRequest pageRequest = pageRequestHelper.map(request.getPageRequest(), entityClass, defaultSortColumn);
        Page<T> requests = getList(predicate, pageRequest);
        return new ListOperationResponse<>(PageRequestHelper.removeSecondarySortingField(requests, defaultSortColumn));
    }

    private Page<T> getList(Predicate predicate, PageRequest pageRequest) {
        return repository.findAll(predicate, pageRequest) ;
    }

    protected abstract Predicate getPredicate(Map<String, Object> filter);
}
