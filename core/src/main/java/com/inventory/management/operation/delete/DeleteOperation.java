package com.inventory.management.operation.delete;

import com.inventory.management.domain.EntityLog;
import com.inventory.management.domain.RequestBase;
import com.inventory.management.operation.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;

import static com.inventory.management.util.Constant.APPROVED;
import static com.inventory.management.util.Constant.DELETE;

@RequiredArgsConstructor
public abstract class DeleteOperation<
        D extends EntityLog, J, DR extends CrudRepository<D, J>,
        T extends RequestBase, I, TR extends CrudRepository<T, I>
        >
        implements Operation<DeleteOperationRequest<D, T>, DeleteOperationResponse<D>> {
    private final DR domainRepository;
    private final TR requestRepository;

    @Override
    public DeleteOperationResponse<D> process(DeleteOperationRequest<D, T> request) {
        D entity = request.getDomain();
        T requestEntity = request.getRequest();
        requestEntity.setRequestStatus(APPROVED);
        requestEntity.setRequestType(DELETE);
        requestRepository.save(requestEntity);
        domainRepository.delete(entity);
        return new DeleteOperationResponse<>(entity);
    }


}
