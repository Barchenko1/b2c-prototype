package com.b2c.prototype.dao.cashed;

import java.util.List;
import java.util.Optional;

public interface IEntityStringMapWrapper<E> {

    E getEntity(String value);
    Optional<E> getOptionalEntity(String value);
    List<E> getEntityList(List<String> values);
    void putEntity(String key, E entity);
    void updateEntity(String oldKey, String key, E entity);
    void removeEntity(String key);

}
