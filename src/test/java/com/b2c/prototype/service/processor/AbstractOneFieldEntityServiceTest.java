package com.b2c.prototype.service.processor;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDtoUpdate;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public abstract class AbstractOneFieldEntityServiceTest<E> {

    @Mock
    protected IParameterFactory parameterFactory;

    @Mock
    protected IEntityDao dao;

    @Mock
    protected ISingleValueMap singleValueMap;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    protected void verifySaveEntity(E entity) {
        verify(dao).persistEntity(entity);
        verify(singleValueMap).putEntity(entity.getClass(), getFieldName(), entity);
    }

    protected void verifyUpdateEntity(E entity, OneFieldEntityDtoUpdate oneFieldEntityDtoUpdate) {
        String searchParameter = oneFieldEntityDtoUpdate.getOldEntity().getValue();
        Parameter parameter = parameterFactory.createStringParameter(getFieldName(), searchParameter);
        verify(dao).findEntityAndUpdate(entity, parameter);
        verify(singleValueMap).putRemoveEntity(
                entity.getClass(),
                searchParameter,
                oneFieldEntityDtoUpdate.getNewEntity().getValue(),
                entity
        );
    }

    protected void verifyDeleteEntity(E entity, OneFieldEntityDto oneFieldEntityDto) {
        verify(dao).deleteEntity(entity);
        verify(singleValueMap).removeEntity(entity.getClass(), oneFieldEntityDto.getValue());
    }

    protected abstract String getFieldName();
}
