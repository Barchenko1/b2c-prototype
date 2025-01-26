package com.b2c.prototype.service.processor;

import java.util.List;
import java.util.Map;

public interface IConstantProcessorService {
    void saveConstantEntity(final Map<String, Object> payload, final String serviceId);
    void putConstantEntity(final Map<String, Object> payload, final String serviceId, final String value);
    void patchConstantEntity(final Map<String, Object> payload, final String serviceId, final String value);
    void deleteConstantEntity(final String serviceId, final String value);
    List<?> getConstantEntities(final String location, final String serviceId);
    Object getConstantEntity(final String location, final String serviceId, final String value);
}

