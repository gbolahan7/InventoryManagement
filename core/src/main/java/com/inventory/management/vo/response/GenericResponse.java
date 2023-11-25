package com.inventory.management.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse<T> {

    public static final String SUCCESS_KEY = "Success";
    public static final String FAILED_KEY = "Failed";

    private String status;
    private String message;
    private T data;

}
