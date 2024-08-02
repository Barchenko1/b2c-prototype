package com.b2c.prototype.service.client;

public interface IService {
    <E> void saveEntity(RequestEntityWrapper<E> requestEntityWrapper);
    <E> void updateEntity(RequestEntityWrapper<E> requestEntityWrapper);
    <E> void deleteEntity(RequestEntityWrapper<E> requestEntityWrapper);
}
