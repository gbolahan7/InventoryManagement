package com.inventory.management.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class LocaleHelper {
    private final MessageSource messageSource;

    public String resolveSubject(String key, Object... args) {
        return resolveSubject(key, Objects.requireNonNull(LocaleContextHolder.getLocaleContext()).getLocale(), args);
    }

    public String resolveSubject(String key, Locale locale, Object... args) {
        try {
            return messageSource.getMessage(key, args, locale);
        }catch (NoSuchMessageException e) {
            return key;
        }
    }

}
