package com.b2c.prototype.configuration;

import com.b2c.prototype.configuration.modal.ApplicationProperty;
import com.b2c.prototype.configuration.modal.TransitiveSelfYaml;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "enums")
@Data
public class ApplicationPropertyConfiguration {
    private Set<ApplicationProperty> deliveryTypes;
    private Set<ApplicationProperty> paymentMethods;
    private Set<ApplicationProperty> orderStatuses;
    private Set<ApplicationProperty> optionGroups;
    private Set<ApplicationProperty> brands;
    private Set<ApplicationProperty> countTypes;
    private Set<ApplicationProperty> countryPhoneCodes;
    private Set<ApplicationProperty> countries;
    private Set<ApplicationProperty> currencies;
    private Set<ApplicationProperty> itemStatuses;
    private Set<ApplicationProperty> itemTypes;
    private Set<Integer> ratings;
    private Set<ApplicationProperty> messageStatuses;
    private Set<ApplicationProperty> messageTypes;

    private Set<TransitiveSelfYaml> categories;
}