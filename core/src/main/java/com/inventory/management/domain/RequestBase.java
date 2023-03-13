package com.inventory.management.domain;

public interface RequestBase {
    String getRequestStatus();
    void setRequestStatus(String requestStatus);
    String getRequestType();
    void setRequestType(String requestType);

}