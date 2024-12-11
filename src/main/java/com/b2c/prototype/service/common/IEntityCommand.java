package com.b2c.prototype.service.common;

public interface IEntityCommand {
    <E> void saveEntity(E entity);
    <E> void updateEntity(E entity);
    <E> void deleteEntity(E entity);
}
