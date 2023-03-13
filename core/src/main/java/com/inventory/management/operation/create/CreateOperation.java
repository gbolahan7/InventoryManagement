package com.inventory.management.operation.create;

import com.inventory.management.domain.RequestBase;
import com.inventory.management.operation.Operation;
import com.inventory.management.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.CrudRepository;

import static com.inventory.management.util.Constant.*;

@AllArgsConstructor
public abstract class CreateOperation<T extends RequestBase, I, R extends CrudRepository<T, I>> implements Operation<CreateOperationRequest<T>, CreateOperationResponse<T>> {
    private final R repository;
    private final Validator<String, T> validator;

    @Override
    public CreateOperationResponse<T> process(CreateOperationRequest<T> request) {
        T requestEntity = request.getRequest();
        if(requestEntity.getRequestType() != null && requestEntity.getRequestType().equals(CREATE)) validator.validate(CREATE, requestEntity);
        requestEntity.setRequestStatus(PENDING);
        return new CreateOperationResponse<>(repository.save(requestEntity));
    }
}
