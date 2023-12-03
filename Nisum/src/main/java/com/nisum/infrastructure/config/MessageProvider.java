package com.nisum.infrastructure.config;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageProvider<T> {

    private final MessageSource messageSource;

    public MessageProvider(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, null, locale);
    }
}