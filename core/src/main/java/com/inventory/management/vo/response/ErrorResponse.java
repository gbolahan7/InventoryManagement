package com.inventory.management.vo.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse {
    private Short statusCode;
    private String param;
    private Object message;
}
