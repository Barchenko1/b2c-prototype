package com.b2c.prototype.manager;

import java.util.List;
import java.util.Optional;

public interface IConstantEntityManager<T> {
    void saveEntity(T payload);
    void updateEntity(String searchValue, T payload);
    void deleteEntity(String value);
    T getEntity(String value);
    Optional<T> getEntityOptional(String value);
    List<T> getEntities();

}
