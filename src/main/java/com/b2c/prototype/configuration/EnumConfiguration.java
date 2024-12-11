package com.b2c.prototype.configuration;

import com.b2c.prototype.configuration.modal.TransitiveSelfYaml;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "enums")
@Data
public class EnumConfiguration {
    private Set<String> deliveryTypes;
    private Set<String> paymentMethods;
    private Set<String> orderStatuses;
    private Set<String> optionGroups;
    private Set<String> brands;
    private Set<String> countTypes;
    private Set<String> countryPhoneCodes;
    private Set<String> countries;
    private Set<String> currencies;
    private Set<String> itemStatuses;
    private Set<String> itemTypes;
    private Set<Integer> ratings;
    private Set<String> messageStatuses;
    private Set<String> messageTypes;

    private Set<TransitiveSelfYaml> categories;
}