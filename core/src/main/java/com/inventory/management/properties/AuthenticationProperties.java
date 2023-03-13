package com.inventory.management.properties;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

@ConfigurationProperties(prefix = "auth")
@Configuration
@Setter
public class AuthenticationProperties {
    final private String randomGenerator;
    private String signingToken;
    private Boolean useRandom;
    private Integer tokenExpiration;

    public AuthenticationProperties(String randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    public String getSigningToken() {
        if(useRandom) return randomGenerator;
        return signingToken;
    }

    public Integer getTokenExpiration() {
        return tokenExpiration;
    }
}
