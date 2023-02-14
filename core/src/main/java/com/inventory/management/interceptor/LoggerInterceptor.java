package com.inventory.management.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggerInterceptor {

    @Pointcut(
        "within(@org.springframework.stereotype.Repository *)" +
        " || within(@org.springframework.stereotype.Service *)" +
        " || within(@org.springframework.web.bind.annotation.RestController *)"
    )
    public void springBeanPointcut() {
        //spring component pointcut
    }

    @Pointcut(
        "within(com.inventory.management.repository..*)" +
        " || within(com.inventory.management.service..*)" +
        " || within(com.inventory.management.controller..*)"
    )
    public void applicationPackagePointcut() {
        //package pointcut
    }

    private void logSignature(JoinPoint joinPoint) {
         log.info(joinPoint.getSignature().getDeclaringTypeName());
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void wrapException(JoinPoint joinPoint, Throwable e) {
        logSignature(joinPoint);
        log.error( "Exception in {}() with cause = {}", joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL" );
    }

    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object wrapLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        logSignature(joinPoint);
        log.info("Class : {}", joinPoint.getSignature().getDeclaringTypeName());
        log.debug("Enter: {}() ", joinPoint.getSignature().getName());
        try {
            Object result = joinPoint.proceed();
            log.debug("Exit: {}() with result = {}", joinPoint.getSignature().getName(), result);
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}()", Arrays.toString(joinPoint.getArgs()), joinPoint.getSignature().getName());
            throw e;
        }
    }
}
