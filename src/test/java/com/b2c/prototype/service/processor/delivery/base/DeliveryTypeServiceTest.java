package com.b2c.prototype.service.processor.delivery.base;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDtoUpdate;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityServiceTest;
import com.tm.core.processor.finder.parameter.Parameter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class DeliveryTypeServiceTest extends AbstractOneFieldEntityServiceTest<DeliveryType> {

    @InjectMocks
    private DeliveryTypeService deliveryTypeService;

    @Override
    protected String getFieldName() {
        return "value";
    }

    @Test
    public void testSaveEntity() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        DeliveryType testValue = createTestValue();

        deliveryTypeService.saveEntity(dto);

        verifySaveEntity(testValue);
    }

    @Test
    public void testUpdateEntity() {
        OneFieldEntityDto oldDto = new OneFieldEntityDto("oldValue");
        OneFieldEntityDto newDto = new OneFieldEntityDto("newValue");
        OneFieldEntityDtoUpdate dtoUpdate = new OneFieldEntityDtoUpdate();
        dtoUpdate.setOldEntityDto(oldDto);
        dtoUpdate.setNewEntityDto(newDto);

        DeliveryType testValue = DeliveryType.builder()
                .value("newValue")
                .build();

        deliveryTypeService.updateEntity(dtoUpdate);

        verifyUpdateEntity(testValue, dtoUpdate);
    }

    @Test
    public void testDeleteEntity() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        DeliveryType testValue = createTestValue();

        deliveryTypeService.deleteEntity(dto);

        verifyDeleteEntity(testValue, dto);
    }

    @Test
    public void testGetEntity() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        Parameter parameter = parameterFactory.createStringParameter(getFieldName(), dto.getValue());
        DeliveryType testValue = createTestValue();

        when(parameterFactory.createStringParameter(getFieldName(), dto.getValue())).thenReturn(parameter);
        when(dao.getEntity(parameter)).thenReturn(testValue);

        DeliveryType result = deliveryTypeService.getEntity(dto);

        assertEquals(testValue, result);
    }

    @Test
    public void testGetEntityOptional() {
        OneFieldEntityDto dto = new OneFieldEntityDto("testValue");
        Parameter parameter = parameterFactory.createStringParameter(getFieldName(), dto.getValue());
        DeliveryType testValue = createTestValue();

        when(parameterFactory.createStringParameter(getFieldName(), dto.getValue())).thenReturn(parameter);
        when(dao.getOptionalEntity(parameter)).thenReturn(Optional.of(testValue));

        Optional<DeliveryType> result = deliveryTypeService.getEntityOptional(dto);

        assertEquals(Optional.of(testValue), result);
    }

    private DeliveryType createTestValue() {
        return DeliveryType.builder()
                .value("testValue")
                .build();
    }
}