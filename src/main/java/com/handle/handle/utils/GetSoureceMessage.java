package com.handle.handle.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GetSoureceMessage {
    private final MessageSource messageSource;

    public GetSoureceMessage(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessageException(String code, Object... args) {
        return messageSource.getMessage("validation."+ code, args, LocaleContextHolder.getLocale());
    }
}
