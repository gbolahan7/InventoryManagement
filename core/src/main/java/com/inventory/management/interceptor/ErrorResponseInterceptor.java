package com.inventory.management.interceptor;

import com.inventory.management.util.LocaleHelper;
import com.inventory.management.vo.problem.CustomApiException;
import com.inventory.management.vo.problem.ValidationException;
import com.inventory.management.vo.problem.ValidatorError;
import com.inventory.management.vo.response.ErrorResponse;
import com.inventory.management.vo.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ErrorResponseInterceptor {

    //TODO internationalize this message
    final public static String ERROR_MESSAGE = "A problem has occurred";

    private final LocaleHelper localeHelper;


    @ExceptionHandler
    public ResponseEntity<GenericResponse<ErrorResponse>> handleAllException(Exception ex, Locale locale) {
        log.info("START: writing generic error response to object..");

        ErrorResponse errorResponse = problemDelegator(ex, locale);
        HttpStatus finalStatus = errorResponse.getStatusCode() != null ? HttpStatus.valueOf(errorResponse.getStatusCode()) : HttpStatus.INTERNAL_SERVER_ERROR;

        GenericResponse<ErrorResponse> response = new GenericResponse<>();
        response.setStatus(GenericResponse.FAILED_KEY);
        response.setMessage(localeHelper.resolveSubject(ERROR_MESSAGE, locale));
        response.setData(errorResponse);

        log.info("END: writing generic error response to object..");
        return ResponseEntity.status(finalStatus).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(response);
    }

    private ErrorResponse problemDelegator(Exception exception, Locale locale) {
        if (exception instanceof CustomApiException)
            return problemDelegator((CustomApiException) exception, locale);
        else if (exception instanceof ValidationException)
            return problemDelegator((ValidationException) exception, locale);
        else if (exception instanceof ResponseStatusException)
            return problemDelegator((ResponseStatusException) exception);
        else
            return problemDelegator(exception);
    }

    private ErrorResponse problemDelegator(CustomApiException ex, Locale locale) {
        ErrorResponse response = new ErrorResponse();
        String message;
        if (ex.isLocaleException()) {
            message = localeHelper.resolveSubject(ex.getLocaleKey(), locale, ex.getArguments());
        } else {
            message = ex.getMessage();
        }
        response.setMessage(message);
        response.setStatusCode((short) HttpStatus.BAD_REQUEST.value());
        return response;
    }

    private ErrorResponse problemDelegator(ValidationException ex, Locale locale) {
        ErrorResponse response = new ErrorResponse();
        Function<ValidatorError, String> localeMapper = validatorError ->
                localeHelper.resolveSubject(validatorError.getErrorKey(), locale, validatorError.getArguments());
        List<String> messages = CollectionUtils.isEmpty(ex.getValidatorErrors()) ? List.of()
                : ex.getValidatorErrors().stream().filter(Objects::nonNull).map(localeMapper).collect(Collectors.toList());
        response.setStatusCode((short) HttpStatus.UNPROCESSABLE_ENTITY.value());
        response.setMessage(messages);
        return response;
    }

    private ErrorResponse problemDelegator(ResponseStatusException responseStatusException) {
        ErrorResponse response = new ErrorResponse();
        response.setStatusCode((short) responseStatusException.getRawStatusCode());
        response.setMessage(responseStatusException.getMessage());
        return response;
    }

    private ErrorResponse problemDelegator(Exception exception) {
        ErrorResponse response = new ErrorResponse();
        response.setStatusCode(((short) HttpStatus.INTERNAL_SERVER_ERROR.value()));
        response.setMessage(exception.getMessage());
        return response;
    }

}
