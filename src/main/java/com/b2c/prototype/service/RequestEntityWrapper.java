package com.b2c.prototype.service;

import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDtoUpdate;
import com.b2c.prototype.dao.wrapper.IEntityStringMapWrapper;
import com.tm.core.dao.single.ISingleEntityDao;
import lombok.Data;

@Data
public class RequestEntityWrapper<E> {

    private final String sqlQuery;
    private final ISingleEntityDao entityDao;
    private final RequestOneFieldEntityDto requestOneFieldEntityDto;
    private final RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate;
    private final E entity;
    private final IEntityStringMapWrapper<E> entityMapWrapper;

    public RequestEntityWrapper(ISingleEntityDao entityDao,
                                RequestOneFieldEntityDto requestOneFieldEntityDto,
                                E entity,
                                IEntityStringMapWrapper<E> entityMapWrapper) {
        this.sqlQuery = null;
        this.entityDao = entityDao;
        this.requestOneFieldEntityDto = requestOneFieldEntityDto;
        this.requestOneFieldEntityDtoUpdate = null;
        this.entity = entity;
        this.entityMapWrapper = entityMapWrapper;
    }

    public RequestEntityWrapper(String sqlQuery,
                                ISingleEntityDao entityDao,
                                RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate,
                                E entity,
                                IEntityStringMapWrapper<E> entityMapWrapper) {
        this.sqlQuery = sqlQuery;
        this.entityDao = entityDao;
        this.requestOneFieldEntityDto = null;
        this.requestOneFieldEntityDtoUpdate = requestOneFieldEntityDtoUpdate;
        this.entity = entity;
        this.entityMapWrapper = entityMapWrapper;
    }

    public RequestEntityWrapper(String sqlQuery,
                                ISingleEntityDao entityDao,
                                RequestOneFieldEntityDto requestOneFieldEntityDto,
                                IEntityStringMapWrapper<E> entityMapWrapper) {
        this.sqlQuery = sqlQuery;
        this.entityDao = entityDao;
        this.requestOneFieldEntityDto = requestOneFieldEntityDto;
        this.requestOneFieldEntityDtoUpdate = null;
        this.entity = null;
        this.entityMapWrapper = entityMapWrapper;
    }

    //

    public RequestEntityWrapper(ISingleEntityDao entityDao,
                                RequestOneFieldEntityDto requestOneFieldEntityDto,
                                E entity) {
        this.sqlQuery = null;
        this.entityDao = entityDao;
        this.requestOneFieldEntityDto = requestOneFieldEntityDto;
        this.requestOneFieldEntityDtoUpdate = null;
        this.entity = entity;
        this.entityMapWrapper = null;
    }

    public RequestEntityWrapper(String sqlQuery,
                                ISingleEntityDao entityDao,
                                RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate,
                                E entity) {
        this.sqlQuery = sqlQuery;
        this.entityDao = entityDao;
        this.requestOneFieldEntityDto = null;
        this.requestOneFieldEntityDtoUpdate = requestOneFieldEntityDtoUpdate;
        this.entity = entity;
        this.entityMapWrapper = null;
    }

    public RequestEntityWrapper(String sqlQuery,
                                ISingleEntityDao entityDao,
                                RequestOneFieldEntityDto requestOneFieldEntityDto) {
        this.sqlQuery = sqlQuery;
        this.entityDao = entityDao;
        this.requestOneFieldEntityDto = requestOneFieldEntityDto;
        this.requestOneFieldEntityDtoUpdate = null;
        this.entity = null;
        this.entityMapWrapper = null;
    }



}
