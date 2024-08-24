package com.b2c.prototype.service;

import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDtoUpdate;
import com.tm.core.dao.single.ISingleEntityDao;

import java.util.Optional;

public abstract class AbstractService implements IService {

    @Override
    public <E> void saveEntity(RequestEntityWrapper<E> requestEntityWrapper) {
        ISingleEntityDao entityDao = requestEntityWrapper.getEntityDao();
        E entity = requestEntityWrapper.getEntity();
        RequestOneFieldEntityDto requestOneFieldEntityDto = requestEntityWrapper.getRequestOneFieldEntityDto();

        entityDao.saveEntity(entity);
        Optional.of(requestEntityWrapper.getEntityMapWrapper())
                .ifPresent(entityMapWrapper ->
                        entityMapWrapper.putEntity(requestOneFieldEntityDto.getRequestValue(), entity));
    }

    @Override
    public <E> void updateEntity(RequestEntityWrapper<E> requestEntityWrapper) {
        String sqlQuery = requestEntityWrapper.getSqlQuery();
        ISingleEntityDao entityDao = requestEntityWrapper.getEntityDao();
        E entity = requestEntityWrapper.getEntity();
        RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate = requestEntityWrapper.getRequestOneFieldEntityDtoUpdate();

        RequestOneFieldEntityDto oldEntityDto = requestOneFieldEntityDtoUpdate.getOldEntityDto();
        RequestOneFieldEntityDto newEntityDto = requestOneFieldEntityDtoUpdate.getNewEntityDto();

        entityDao.mutateEntityBySQLQueryWithParams(sqlQuery);
        Optional.of(requestEntityWrapper.getEntityMapWrapper())
                .ifPresent(entityMapWrapper ->
                        entityMapWrapper.updateEntity(
                                oldEntityDto.getRequestValue(),
                                newEntityDto.getRequestValue(),
                                entity));

    }

    @Override
    public <E> void deleteEntity(RequestEntityWrapper<E> requestEntityWrapper) {
        String sqlQuery = requestEntityWrapper.getSqlQuery();
        ISingleEntityDao entityDao = requestEntityWrapper.getEntityDao();
        RequestOneFieldEntityDto requestOneFieldEntityDto = requestEntityWrapper.getRequestOneFieldEntityDto();

        entityDao.mutateEntityBySQLQueryWithParams(sqlQuery, requestOneFieldEntityDto.getRequestValue());
        Optional.of(requestEntityWrapper.getEntityMapWrapper())
                        .ifPresent(entityMapWrapper ->
                                entityMapWrapper.removeEntity(requestOneFieldEntityDto.getRequestValue()));
    }

}
