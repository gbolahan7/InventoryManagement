package com.inventory.management.vo.problem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidatorError {

    private String errorKey;
    private Object[] arguments;

}