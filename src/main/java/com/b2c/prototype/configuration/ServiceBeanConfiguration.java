package com.b2c.prototype.configuration;

import com.b2c.prototype.dao.ISessionEntityFetcher;
import com.b2c.prototype.dao.SessionEntityFetcher;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.transform.function.TransformationFunctionService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.finder.factory.ParameterFactory;
import com.tm.core.process.dao.query.IQueryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBeanConfiguration {

    @Value("${thread.pool.size}")
    private int threadCount;

    // support service

    @Bean
    public IParameterFactory parameterFactory() {
        return new ParameterFactory();
    }

    @Bean
    public ITransformationFunctionService transformationFunctionService() {
        return new TransformationFunctionService();
    }

    @Bean
    public ISessionEntityFetcher sessionEntityFetcher(IQueryService queryService, IParameterFactory parameterFactory) {
        return new SessionEntityFetcher(queryService, parameterFactory);
    }

}
