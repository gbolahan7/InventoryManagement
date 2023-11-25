package com.inventory.management.operation.list;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Getter
public class ListAuditOperationRequest<I> extends ListOperationRequest {
    private final I identifier;

    public ListAuditOperationRequest(Serializable pageRequest, Map<String, Object> filter, I identifier) {
        super(pageRequest, filter);
        this.identifier = identifier;
    }
}
