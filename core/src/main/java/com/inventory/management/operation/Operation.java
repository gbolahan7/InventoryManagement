package com.inventory.management.operation;

public interface Operation<Q extends OperationRequest, S extends OperationResponse> {

    S process(Q request) ;
}

