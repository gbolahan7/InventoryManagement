package com.inventory.management.vo.problem;

import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class CustomApiException extends RuntimeException {

    private String localeKey;
    private Object[] arguments;

    public CustomApiException(String message) {
        super(message);
    }

    public CustomApiException(String localeKey, Object... arguments) {
        super("error.custom");
        this.localeKey = localeKey;
        this.arguments = arguments;
    }

    public boolean isLocaleException() {
        return StringUtils.hasText(localeKey);
    }

}
