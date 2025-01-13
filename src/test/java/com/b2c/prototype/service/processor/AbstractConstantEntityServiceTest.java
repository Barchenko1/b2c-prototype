package com.b2c.prototype.service.processor;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.payload.ConstantEntityPayloadDto;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.b2c.prototype.util.Constant.VALUE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

public abstract class AbstractConstantEntityServiceTest<E> {

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
        verify(singleValueMap).putEntity(entity.getClass(), VALUE, entity);
    }

    protected void verifyUpdateEntity(E entity, ConstantEntityPayloadDto constantEntityPayloadDto) {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, "testValue");
        verify(dao).findEntityAndUpdate(entity, parameter);
        verify(singleValueMap).putRemoveEntity(
                entity.getClass(),
                "testValue",
                constantEntityPayloadDto.getValue(),
                entity
        );
    }

    protected void verifyDeleteEntity(String value) {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, value);
        verify(dao).findEntityAndDelete(parameter);
        verify(singleValueMap).removeEntity(any(), eq(value));
    }

    protected ConstantEntityPayloadDto getResponseOneFieldEntityDto() {
        return ConstantEntityPayloadDto.builder()
                .value("testValue")
                .label("label")
                .build();
    }

}
