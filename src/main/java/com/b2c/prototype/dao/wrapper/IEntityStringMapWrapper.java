package com.b2c.prototype.dao.wrapper;

import java.util.List;

public interface IEntityStringMapWrapper<E> {

    E getEntity(String value);
    List<E> getEntityList(List<String> values);
    void putEntity(String key, E entity);
    void updateEntity(String oldKey, String key, E entity);
    void removeEntity(String key);

}
