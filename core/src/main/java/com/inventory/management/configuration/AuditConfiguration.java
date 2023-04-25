package com.inventory.management.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditConfiguration {

    @Bean
    public AuditorAware<String> securityAuditorAware() {
        return () -> {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if( auth != null && auth.getPrincipal() != null) {
                Object principal = auth.getPrincipal();
                if(principal instanceof String) return Optional.of("SYSTEM");
                return Optional.of(((Principal) auth.getPrincipal()).getName());
            }
            else return  Optional.of("SYSTEM");
        };
    }
}
