package com.b2c.prototype.manager;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.mockito.Mockito.verify;

public abstract class AbstractConstantEntityManagerTest<E> {

    @Mock
    protected IGeneralEntityDao dao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    protected void verifySaveEntity(E entity) {
        verify(dao).persistEntity(entity);
    }

    protected void verifyUpdateEntity(E entity, String newValue) {
//        
//        verify(dao).findEntityAndUpdate(entity, parameter);
    }

    protected void verifyDeleteEntity(String value) {
//        Parameter parameter = parameterFactory.createStringParameter(VALUE, value);
//        verify(dao).findEntityAndDelete(parameter);
    }

    protected ConstantPayloadDto getResponseOneFieldEntityDto() {
        return ConstantPayloadDto.builder()
                .value("testValue")
                .label("label")
                .build();
    }

}
