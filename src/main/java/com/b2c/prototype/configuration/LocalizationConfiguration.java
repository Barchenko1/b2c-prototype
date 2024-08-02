package com.b2c.prototype.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class LocalizationConfiguration {

    private static final String BASE_NAME = "classpath:i18n/configuration";
    private static final String FILE_PATTERN = "classpath:i18n/configuration_*.properties";
    private static final Pattern LOCALE_PATTERN = Pattern.compile("configuration_(\\w+).properties");

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(BASE_NAME);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public Set<Locale> availableLocales() throws IOException {
        Set<Locale> locales = new HashSet<>();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        var resources = resolver.getResources(FILE_PATTERN);

        for (var resource : resources) {
            String filename = resource.getFilename();
            if (filename != null) {
                Matcher matcher = LOCALE_PATTERN.matcher(filename);
                if (matcher.find()) {
                    String languageTag = matcher.group(1);
                    locales.add(Locale.forLanguageTag(languageTag));
                }
            }
        }
        return locales;
    }
}
