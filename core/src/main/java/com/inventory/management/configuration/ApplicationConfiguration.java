package com.inventory.management.configuration;

import com.inventory.management.repository.RoleRepository;
import com.inventory.management.util.StartupHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.function.Supplier;

@Configuration
public class ApplicationConfiguration {

    @Lazy(value = false)
    @Bean
    StartupHelper loadStartup(RoleRepository roleRepository) {
        return new StartupHelper(roleRepository);
    }

    @Bean("randomGenerator")
    String generateRandom() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}
