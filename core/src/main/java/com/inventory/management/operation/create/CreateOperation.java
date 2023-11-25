package com.inventory.management.operation.create;

import com.inventory.management.domain.RequestBase;
import com.inventory.management.operation.Operation;
import com.inventory.management.validation.AttachmentValidation;
import com.inventory.management.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.repository.CrudRepository;
import org.springframework.util.StringUtils;

import static com.inventory.management.util.Constant.*;

@RequiredArgsConstructor
public abstract class CreateOperation<T extends RequestBase, I, R extends CrudRepository<T, I>> implements Operation<CreateOperationRequest<T>, CreateOperationResponse<T>> {
    private final R repository;
    private final Validator<String, T> validator;
    private final AttachmentValidation attachmentValidation = new AttachmentValidation();

    @Override
    public CreateOperationResponse<T> process(CreateOperationRequest<T> request) {
        T requestEntity = request.getRequest();
        if(requestEntity.getRequestType() != null && requestEntity.getRequestType().equals(CREATE)) validator.validate(CREATE, requestEntity);
        if(requestEntity.getRequestType() != null && requestEntity.getRequestType().equals(MODIFY)) validator.validate(MODIFY, requestEntity);
        validateAttachment(requestEntity);
        requestEntity.setRequestStatus(PENDING);
        return new CreateOperationResponse<>(repository.save(requestEntity));
    }

    void validateAttachment(T entity) {
        AttachmentPayload attachmentPayload = new AttachmentPayload();
        BeanUtils.copyProperties(entity, attachmentPayload);
        if(StringUtils.hasText(attachmentPayload.picturePayload)) {
            attachmentValidation.validateAttachment(attachmentPayload.picturePayload);
        }
    }

    @Getter
    @Setter
    private class AttachmentPayload {
        private String picturePayload;
    }
}
