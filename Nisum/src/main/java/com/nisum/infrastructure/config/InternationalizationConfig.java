package com.nisum.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@Configuration
public class InternationalizationConfig {
    @Value("${spring.messages.basename}")
    private String baseName;

    @Value("${spring.mvc.locale.default-locale}")
    private String localeLang;

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(baseName);
        Locale.setDefault(new Locale(localeLang));
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}