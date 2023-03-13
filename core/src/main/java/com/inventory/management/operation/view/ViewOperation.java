package com.inventory.management.operation.view;

import com.inventory.management.operation.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

@AllArgsConstructor
public abstract class ViewOperation<T, I, R extends JpaRepository<T, I>> implements Operation<ViewOperationRequest<I>, ViewOperationResponse<T>> {
    private final R repository;

    @Override
    public ViewOperationResponse<T> process(ViewOperationRequest<I> request) {
        return new ViewOperationResponse<>(repository.findById(request.getIdentifier()).orElse(null));
    }
}
