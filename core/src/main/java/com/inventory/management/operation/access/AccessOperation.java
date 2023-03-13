package com.inventory.management.operation.access;

import com.inventory.management.domain.EntityLog;
import com.inventory.management.domain.RequestBase;
import com.inventory.management.operation.Operation;
import com.inventory.management.vo.problem.ValidationBuilder;
import com.inventory.management.vo.problem.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Objects;

import static com.inventory.management.util.Constant.PENDING;

@AllArgsConstructor
public abstract class AccessOperation<T extends EntityLog & RequestBase, I, R extends JpaRepository<T, I>> implements Operation<AccessOperationRequest<I>, AccessOperationResponse<T>> {
    protected final R repository;

    @Override
    public AccessOperationResponse<T> process(AccessOperationRequest<I> request) {
        if (Objects.isNull(request))
            throw new IllegalArgumentException("Reject Request must not be null");

        if (Objects.isNull(request.getIdentifier()))
            throw new IllegalArgumentException("Reject Request Identifier must not be null");

        T requestEntity = repository.findById(request.getIdentifier())
                .orElseThrow(() -> new ValidationException(new ValidationBuilder().addError("request.does.not.exist").build()));
        validateRequestEntity(requestEntity);
        return new AccessOperationResponse<>(operate(requestEntity));
    }

    private void validateRequestEntity(T requestEntity) {
        if (!PENDING.equals(requestEntity.getRequestStatus()))
            throw new ValidationException(new ValidationBuilder().addError("request.already.processed").build());
    }

    protected abstract T operate(T requestEntity);
}
