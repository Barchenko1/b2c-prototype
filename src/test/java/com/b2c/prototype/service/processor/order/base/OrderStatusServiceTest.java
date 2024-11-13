package com.b2c.prototype.service.processor.order.base;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDtoUpdate;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityServiceTest;
import com.tm.core.processor.finder.parameter.Parameter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class OrderStatusServiceTest extends AbstractOneFieldEntityServiceTest<OrderStatus> {

    @InjectMocks
    private OrderStatusService service;
    
    @Override
    protected String getFieldName() {
        return "value";
    }

    @Test
    public void testSaveEntity() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        OrderStatus testValue = createTestValue();

        service.saveEntity(dto);

        verifySaveEntity(testValue);
    }

    @Test
    public void testUpdateEntity() {
        OneFieldEntityDto oldDto = new OneFieldEntityDto("oldValue");
        OneFieldEntityDto newDto = new OneFieldEntityDto("newValue");
        OneFieldEntityDtoUpdate dtoUpdate = new OneFieldEntityDtoUpdate();
        dtoUpdate.setOldEntityDto(oldDto);
        dtoUpdate.setNewEntityDto(newDto);

        OrderStatus testValue = OrderStatus.builder()
                .value("newValue")
                .build();

        service.updateEntity(dtoUpdate);

        verifyUpdateEntity(testValue, dtoUpdate);
    }

    @Test
    public void testDeleteEntity() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        OrderStatus testValue = createTestValue();

        service.deleteEntity(dto);

        verifyDeleteEntity(testValue, dto);
    }

    @Test
    public void testGetEntity() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        Parameter parameter = parameterFactory.createStringParameter(getFieldName(), dto.getValue());
        OrderStatus testValue = createTestValue();

        when(parameterFactory.createStringParameter(getFieldName(), dto.getValue())).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);

        OrderStatus result = service.getEntity(dto);

        assertEquals(testValue, result);
    }

    @Test
    public void testGetEntityOptional() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        Parameter parameter = parameterFactory.createStringParameter(getFieldName(), dto.getValue());
        OrderStatus testValue = createTestValue();

        when(parameterFactory.createStringParameter(getFieldName(), dto.getValue())).thenReturn(parameter);
        when(dao.getOptionalEntity(parameter)).thenReturn(Optional.of(testValue));

        Optional<OrderStatus> result = service.getEntityOptional(dto);

        assertEquals(Optional.of(testValue), result);
    }

    private OrderStatus createTestValue() {
        return OrderStatus.builder()
                .value("testValue")
                .build();
    }
}