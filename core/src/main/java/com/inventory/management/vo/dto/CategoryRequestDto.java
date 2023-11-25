package com.inventory.management.vo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryRequestDto extends CategoryDto{
    private long requestId;
    private String requestType;
    private String requestStatus;
    private Long categoryId;
    private String status;
}
