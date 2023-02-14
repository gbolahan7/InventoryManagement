package com.inventory.management.interceptor;


import com.inventory.management.annotation.ResponseWrapper;
import com.inventory.management.vo.response.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.InputStream;

import static com.inventory.management.interceptor.ErrorResponseInterceptor.ERROR_MESSAGE;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class SuccessResponseInterceptor implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.getContainingClass().isAnnotationPresent(ResponseWrapper.class) ||
                methodParameter.hasMethodAnnotation(ResponseWrapper.class);
    }

    @Override
    public Object beforeBodyWrite(Object object, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        log.info("START: writing generic response to object..");
        if (object instanceof InputStream || object instanceof InputStreamSource) return object;
        String message = extractAnnotatedMessage(methodParameter);
        log.info("Payload gotten: {}", object);

        GenericResponse<Object> genericResponse = new GenericResponse<>();
        genericResponse.setStatus(GenericResponse.SUCCESS_KEY);
        genericResponse.setMessage(message);
        genericResponse.setData(object);

        setFailureStatus(response, genericResponse);

        log.info("END: writing generic response to object..");
        return genericResponse;
    }

    private void setFailureStatus(ServerHttpResponse response, GenericResponse<Object> genericResponse) {
        if (response instanceof ServletServerHttpResponse) {
            int statusCode = ((ServletServerHttpResponse) response).getServletResponse().getStatus();
            HttpStatus status = HttpStatus.valueOf(statusCode);
            if (status.is4xxClientError() || status.is5xxServerError()) {
                genericResponse.setStatus(GenericResponse.FAILED_KEY);
                genericResponse.setMessage(ERROR_MESSAGE);
            }
        }
    }

    private String extractAnnotatedMessage(MethodParameter methodParameter) {
        String message = "";
        ResponseWrapper classWrapper = AnnotationUtils.findAnnotation(methodParameter.getContainingClass(), ResponseWrapper.class);
        ResponseWrapper methodWrapper = AnnotationUtils.findAnnotation(methodParameter.getAnnotatedElement(), ResponseWrapper.class);
        if (classWrapper != null) {
            message = classWrapper.message();
        }
        if (methodWrapper != null) {
            message = methodWrapper.message();
        }
        return message;
    }

}