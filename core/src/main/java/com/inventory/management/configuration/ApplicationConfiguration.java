package com.inventory.management.configuration;

import com.inventory.management.interceptor.VisitorRecorderInterceptor;
import com.inventory.management.repository.PerformanceSettingRepository;
import com.inventory.management.repository.RoleRepository;
import com.inventory.management.util.StartupHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;

import java.util.function.Supplier;

@Configuration
public class ApplicationConfiguration {

    @Lazy(value = false)
    @Bean
    StartupHelper loadStartup(RoleRepository roleRepository, PerformanceSettingRepository performanceSettingRepository) {
        return new StartupHelper(roleRepository, performanceSettingRepository);
    }

    @Bean("randomGenerator")
    String generateRandom() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    @Bean
    @Lazy(value = false)
    public FilterRegistrationBean<VisitorRecorderInterceptor> visitorInterceptorRegistration(VisitorRecorderInterceptor interceptor) {
        final FilterRegistrationBean<VisitorRecorderInterceptor> reg = new FilterRegistrationBean<>(interceptor);
        reg.addUrlPatterns("/*");
        reg.setOrder(Ordered.LOWEST_PRECEDENCE);
        return reg;
    }
}
