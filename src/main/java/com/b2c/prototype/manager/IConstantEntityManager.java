package com.b2c.prototype.manager;

import java.util.List;
import java.util.Optional;

public interface IConstantEntityManager<T> {
    void persistEntity(T entity);
    void mergeEntity(String searchValue, T entity);
    void removeEntity(String value);
    T getEntity(String value);
    Optional<T> getEntityOptional(String value);
    List<T> getEntities();

}
