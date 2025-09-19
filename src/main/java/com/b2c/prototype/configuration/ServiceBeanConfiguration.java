package com.b2c.prototype.configuration;

import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.transform.function.TransformationFunctionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBeanConfiguration {

    // support service
    @Bean
    public ITransformationFunctionService transformationFunctionService() {
        return new TransformationFunctionService();
    }

}
