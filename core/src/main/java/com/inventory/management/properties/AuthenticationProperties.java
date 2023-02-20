package com.inventory.management.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "auth")
@Configuration
@Getter
@Setter
public class AuthenticationProperties {
    private String signingToken;
    private Integer tokenExpiration;

}