package com.b2c.prototype.processor;

public interface IEntityStringMapWrapper<E> {

    E getEntity(String value);
    void putEntity(String key, E entity);
    void updateEntity(String oldKey, String key, E entity);
    void removeEntity(String key);

}
