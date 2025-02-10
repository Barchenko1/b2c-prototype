package com.b2c.prototype.manager;

import com.b2c.prototype.service.scope.IConstantsScope;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.finder.parameter.Parameter;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static com.b2c.prototype.util.Constant.VALUE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public abstract class AbstractConstantEntityManagerTest<E> {

    @Mock
    protected IParameterFactory parameterFactory;
    @Mock
    protected IEntityDao dao;
    @Mock
    protected IConstantsScope singleValueMap;
    @Mock
    protected ITransformationFunctionService transformationFunctionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    protected void verifySaveEntity(E entity) {
        verify(dao).persistEntity(entity);
        verify(singleValueMap).putEntity(entity.getClass(), VALUE, entity);
    }

    protected void verifyUpdateEntity(E entity, String newValue) {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, "testValue");
        verify(dao).findEntityAndUpdate(entity, parameter);
        verify(singleValueMap).putRemoveEntity(
                entity.getClass(),
                "testValue",
                newValue,
                entity
        );
    }

    protected void verifyDeleteEntity(String value) {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, value);
        verify(dao).findEntityAndDelete(parameter);
        verify(singleValueMap).removeEntity(any(), eq(value));
    }

    protected ConstantPayloadDto getResponseOneFieldEntityDto() {
        return ConstantPayloadDto.builder()
                .value("testValue")
                .label("label")
                .build();
    }

}
