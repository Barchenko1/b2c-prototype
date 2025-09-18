package com.b2c.prototype.manager;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.finder.parameter.Parameter;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static com.b2c.prototype.util.Constant.VALUE;
import static org.mockito.Mockito.verify;

public abstract class AbstractConstantEntityManagerTest<E> {

    @Mock
    protected IParameterFactory parameterFactory;
    @Mock
    protected IGeneralEntityDao dao;
    @Mock
    protected ITransformationFunctionService transformationFunctionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    protected void verifySaveEntity(E entity) {
        verify(dao).persistEntity(entity);
    }

    protected void verifyUpdateEntity(E entity, String newValue) {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, "testValue");
//        verify(dao).findEntityAndUpdate(entity, parameter);
    }

    protected void verifyDeleteEntity(String value) {
        Parameter parameter = parameterFactory.createStringParameter(VALUE, value);
//        verify(dao).findEntityAndDelete(parameter);
    }

    protected ConstantPayloadDto getResponseOneFieldEntityDto() {
        return ConstantPayloadDto.builder()
                .value("testValue")
                .label("label")
                .build();
    }

}
