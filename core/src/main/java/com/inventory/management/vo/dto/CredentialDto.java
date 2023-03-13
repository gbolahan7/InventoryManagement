package com.inventory.management.vo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CredentialDto {
    private String username;
    private String email;
    private Set<String> role;
    private String password;
    private String token;
}
