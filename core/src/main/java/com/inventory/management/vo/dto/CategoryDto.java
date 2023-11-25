package com.inventory.management.vo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryDto extends BaseAuditDto {
    private Long id;
    private String name;
    private String description;
    private String status;
    private Set<String> items;

    @JsonIgnore
    public String getItemsTrans() {
        if(CollectionUtils.isEmpty(items)) return "";
        return String.join(",", items);
    }
}
