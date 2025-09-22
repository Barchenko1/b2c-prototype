package com.b2c.prototype.manager;

import java.util.List;
import java.util.Optional;

public interface IIntegerConstantEntityManager<T> {
    void persistEntity(T numberConstantPayloadDto);
    void mergeEntity(Integer searchValue, T numberConstantPayloadDto);
    void removeEntity(int ratingValue);
    T getEntity(int ratingValue);
    Optional<T> getEntityOptional(int ratingValue);
    List<T> getEntities();
}
