package com.b2c.prototype.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class LocalizationInterpreter {

    private final MessageSource messageSource;

    public LocalizationInterpreter(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String interpret(String prefix, String attribute, Locale locale) {
        String fullAttribute = prefix + "." + attribute.toLowerCase();
        return messageSource.getMessage(fullAttribute, null, attribute.toUpperCase(), locale);
    }
}
