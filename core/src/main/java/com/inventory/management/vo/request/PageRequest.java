package com.inventory.management.vo.request;

import lombok.Data;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class PageRequest implements Serializable {
  private Integer page = 0;
  private Integer size = 10;
  @Pattern(regexp="^(asc|desc)$")
  private String sortDirection = "asc";
  private String sortBy;

}

