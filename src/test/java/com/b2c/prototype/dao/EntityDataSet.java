package com.b2c.prototype.dao;

public class EntityDataSet<E> {
    private final E entity;
    private final String dataSetPath;

    public EntityDataSet(E entity, String dataSetPath) {
        this.entity = entity;
        this.dataSetPath = dataSetPath;
    }

    public E getEntity() {
        return entity;
    }

    public String getDataSetPath() {
        return dataSetPath;
    }
}
