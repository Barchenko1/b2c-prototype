package com.b2c.prototype.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "enums")
@Data
public class EnumConfiguration {
    private List<String> deliveryTypes;
    private List<String> paymentMethods;
    private List<String> orderStatuses;
    private List<String> brands;
    private List<String> itemStatuses;
    private List<String> itemTypes;
}