package com.b2c.prototype.dao.wrapper;

import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Map;

public abstract class AbstractEntityIntegerMapWrapper<E> implements IEntityIntegerMapWrapper<E> {

    protected final ISingleEntityDao entityDao;
    protected final Map<Integer, E> entityMap;
    protected final String query;

    public AbstractEntityIntegerMapWrapper(ISingleEntityDao entityDao,
                                           Map<Integer, E> entityMap,
                                           String query) {
        this.entityDao = entityDao;
        this.entityMap = entityMap;
        this.query = query;
    }


}
